package org.rerenderer.android

import android.app.Activity
import android.os.Bundle
import org.jetbrains.anko.*
import org.rerenderer.android.primitives.Image
import org.rerenderer.android.primitives.Rectangle
import org.rerenderer.android.primitives.Text
import org.rerenderer.android.render.FullscreenView


open class RerendererActivity : Activity(), AnkoLogger {
    open val url = "file:///android_asset/index.html"
    var executor: Executor? = null
    var view: FullscreenView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = FullscreenView(ctx)
        setContentView(view)
        registerPrimitives()
        executor = Executor(ctx, url)
        initEvents()
    }

    fun initEvents() {
        events.RenderRequest on {
            async() {
                view!!.render(it.tree, it.scale)
            }
        }
        events.PlatformEvent on {
            executor!!.sendPlatformEvent(it)
        }
        events.PlatformInformation on {
            executor!!.updatePlatformInformation(it.width, it.height)
        }
    }

    open fun registerPrimitives() {
        Rectangle.register()
        Image.register(ctx)
        Text.register()
    }
}
