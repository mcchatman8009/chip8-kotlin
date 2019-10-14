package chip8.application.timer

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class FpsTimer(private val fps: Int = 60, threadName: String = "FPS Timer") {
    private val executor: ScheduledExecutorService

    init {
        executor = Executors.newScheduledThreadPool(1) {
            val newThread = Executors.defaultThreadFactory().newThread(it)

            newThread.name = threadName
            newThread.isDaemon = true

            newThread
        }
    }

    fun startAndInvokeLoopWithRunnable(runnableCommand: () -> Unit) {
        executor.scheduleAtFixedRate(runnableCommand, 0, (1000 / fps).toLong(), TimeUnit.MILLISECONDS)
    }
}

