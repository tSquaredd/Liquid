package com.tsquaredapplications.liquid.common.notifications

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tsquaredapplications.liquid.LiquidApplication
import com.tsquaredapplications.liquid.MainActivity
import com.tsquaredapplications.liquid.R
import com.tsquaredapplications.liquid.common.database.AppDatabase
import com.tsquaredapplications.liquid.common.database.goal.RoomGoalRepository
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.common.database.users.UserManagerImpl
import com.tsquaredapplications.liquid.ext.getStartAndEndTimeForToday
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

class NotificationWorker
@Inject constructor(
    @ApplicationContext private val context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val appDatabase = AppDatabase.getInstance(context)
        val entryDao = appDatabase.entryDao()
        val goalRepository = RoomGoalRepository(appDatabase.goalDao())
        val userInformation = UserManagerImpl(context, goalRepository).getUser()
            ?: return Result.failure()

        val nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val nowMinute = Calendar.getInstance().get(Calendar.MINUTE)
        if ((nowHour > userInformation.notifications.startTime.hour ||
                    nowHour == userInformation.notifications.startTime.hour &&
                    nowMinute >= userInformation.notifications.startTime.min) &&
            (nowHour < userInformation.notifications.endTime.hour ||
                    nowHour == userInformation.notifications.endTime.hour &&
                    nowMinute <= userInformation.notifications.endTime.min)
        ) {
            val todayTimeRange = getStartAndEndTimeForToday()
            val todayEntryTotal =
                entryDao.getAllForTimeRange(todayTimeRange.first, todayTimeRange.second)
                    .map { it.amount }.sum()

            if (todayEntryTotal < userInformation.dailyGoal) {
                val intent = Intent(context, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

                val builder = NotificationCompat.Builder(context, LiquidApplication.CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle(context.getString(R.string.notification_title))
                    .setContentText(buildContentString(todayEntryTotal.toInt(), userInformation))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)

                with(NotificationManagerCompat.from(context)) {
                    notify(NOTIFICATION_ID, builder.build())
                }
            }
        }
        return Result.success()
    }

    private fun buildContentString(progress: Int, userInformation: UserInformation): String {
        val format = context.getString(R.string.notification_content)
        val goalPercentageComplete = (progress / userInformation.dailyGoal.toDouble() * 100).toInt()
        return String.format(format, goalPercentageComplete)
    }

    companion object {
        const val NOTIFICATION_ID = 13311331
    }
}