package com.example.androidpracticumcustomview.ui.theme.xml

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

/*
Задание:
Реализуйте необходимые компоненты;
Создайте проверку что дочерних элементов не более 2-х;
Предусмотрите обработку ошибок рендера дочерних элементов.
Задание по желанию:
Предусмотрите параметризацию длительности анимации.
 */

class CustomContainer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ViewGroup(context, attrs) {

    private var animationDuration: Long = 5000L

    init {
        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var totalHeight = paddingTop + paddingBottom
        var maxWidth = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            totalHeight += child.measuredHeight + 16
            maxWidth = maxOf(maxWidth, child.measuredWidth)
        }

        val finalWidth = resolveSize(maxWidth + paddingLeft + paddingRight, widthMeasureSpec)
        val finalHeight = resolveSize(totalHeight, heightMeasureSpec)

        setMeasuredDimension(finalWidth, finalHeight)
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val parentWidth = right - left
        val parentHeight = bottom - top

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility != View.GONE) {
                val childWidth = child.measuredWidth
                val childHeight = child.measuredHeight
                val childLeft = (parentWidth - childWidth) / 2

                val childTop = when (i) {
                    0 -> paddingTop
                    1 -> parentHeight - paddingBottom - childHeight
                    else -> paddingTop
                }

                child.layout(
                    childLeft,
                    childTop,
                    childLeft + childWidth,
                    childTop + childHeight
                )
            }
        }
    }


    override fun addView(child: View) {
        if (childCount >= 2) {
            throw IllegalStateException("Нельзя добавить больше 2-х элементов")
        }

        super.addView(child)

        child.alpha = 0f
        child.translationY = 0f

        post {
            val containerHeight = height
            val finalTop = child.top
            val centerY = containerHeight / 2f
            val offset = centerY - finalTop

            child.translationY = offset

            child.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(animationDuration)
                .start()
        }
    }
}