package com.deathhit.my_good_doggo_app.fragment.image_viewer

import android.annotation.SuppressLint
import android.graphics.Matrix
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import androidx.core.graphics.values
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.deathhit.my_good_doggo_app.R
import com.deathhit.my_good_doggo_app.databinding.FragmentImageViewerBinding
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ImageViewerFragment : DialogFragment() {
    companion object {
        private const val SCALE_MAX = 4f
        private const val SCALE_MIN = 0.5f

        fun create(imageUrl: String) = ImageViewerFragment().apply {
            arguments = ImageViewerViewModel.createArgs(imageUrl)
        }
    }

    private val binding get() = _binding!!
    private var _binding: FragmentImageViewerBinding? = null

    private val viewModel: ImageViewerViewModel by viewModels()

    private var baseImageScale = 1f

    private val previewMatrix = Matrix()
    private val transformationMatrix = Matrix()

    private val tempValueArray = previewMatrix.values()

    private val onCloseListener = View.OnClickListener {
        viewModel.onClose()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentImageViewerBinding.inflate(inflater, container, false).run {
        _binding = this
        root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.imageView) {
            val scaleGestureDetector = ScaleGestureDetector(requireContext(), object :
                ScaleGestureDetector.SimpleOnScaleGestureListener() {
                override fun onScale(detector: ScaleGestureDetector): Boolean {
                    with(detector) {
                        val scaleFactor = this.scaleFactor  //Avoid duplicate function calls.

                        previewMatrix.set(transformationMatrix)
                        previewMatrix.postScale(
                            scaleFactor,
                            scaleFactor,
                            focusX,
                            focusY
                        )

                        previewMatrix.getValues(tempValueArray)
                        val newScale = tempValueArray[Matrix.MSCALE_X]
                        val originalScale = newScale / scaleFactor
                        val scaleMax = SCALE_MAX * baseImageScale
                        val scaleMin = SCALE_MIN * baseImageScale

                        var newScaleFactor = scaleFactor
                        when {
                            newScale > scaleMax -> newScaleFactor = scaleMax / originalScale
                            newScale < scaleMin -> newScaleFactor = scaleMin / originalScale
                        }

                        transformationMatrix.set(transformationMatrix)  //Reset matrix.
                        transformationMatrix.postScale(
                            newScaleFactor,
                            newScaleFactor,
                            focusX,
                            focusY
                        )

                        imageMatrix = transformationMatrix
                        return true
                    }
                }
            })

            val gestureDetector = GestureDetectorCompat(requireContext(),
                object : GestureDetector.SimpleOnGestureListener() {
                    override fun onDoubleTap(e: MotionEvent?): Boolean {
                        resetTransformationMatrix(drawable)
                        return true
                    }

                    override fun onScroll(
                        e1: MotionEvent?,
                        e2: MotionEvent?,
                        distanceX: Float,
                        distanceY: Float
                    ): Boolean {
                        transformationMatrix.set(transformationMatrix)  //Reset matrix.
                        transformationMatrix.postTranslate(
                            -distanceX,
                            -distanceY
                        )

                        imageMatrix = transformationMatrix
                        return true
                    }
                })

            setOnTouchListener { _, motionEvent ->
                scaleGestureDetector.onTouchEvent(motionEvent)
                    .or(gestureDetector.onTouchEvent(motionEvent)).or(onTouchEvent(motionEvent))
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.eventFlow.collect { event ->
                        when (event) {
                            ImageViewerViewModel.Event.Close -> dismiss()
                        }
                    }
                }

                launch {
                    viewModel.stateFlow.map { it.imageUrl }.distinctUntilChanged().collect {
                        Glide.with(binding.imageView).load(it).listener(object :
                            RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean = false

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                resource?.let { resetTransformationMatrix(resource) }
                                return false
                            }
                        }).into(binding.imageView)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            buttonClose.setOnClickListener(onCloseListener)
        }
    }

    override fun onPause() {
        super.onPause()
        with(binding) {
            buttonClose.setOnClickListener(null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getTheme(): Int = R.style.Theme_MyGoodDoggoApp_FullScreenDialog

    private fun resetTransformationMatrix(drawable: Drawable) {
        with(binding.imageView) {
            val imageRectF = RectF(
                0f,
                0f,
                drawable.intrinsicWidth.toFloat(),
                drawable.intrinsicHeight.toFloat()
            )
            val viewRectF = RectF(0f, 0f, width.toFloat(), height.toFloat())
            transformationMatrix.setRectToRect(imageRectF, viewRectF, Matrix.ScaleToFit.CENTER)

            transformationMatrix.getValues(tempValueArray)
            baseImageScale = tempValueArray[Matrix.MSCALE_X]

            imageMatrix = transformationMatrix
        }
    }
}