package com.characters.rickandmorty.core
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.characters.rickandmorty.R
import com.characters.rickandmorty.application.AppConstants
import com.characters.rickandmorty.repository.CharacterRepositoryImpl
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class FirebaseMessagingService: FirebaseMessagingService() {
//
//        private fun getTokenForNotification(){
//        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
//            task.result?.let {
//                Log.e("aaaa", task.result.toString())
//            }
//        }
//    }
//

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    @Inject
    lateinit var repository : CharacterRepositoryImpl

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        scope.launch {
          repository.registerToken(token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if(message.notification != null){
            createChannel()
            scope.launch { createSimpleNotification(message) }
        }
    }

    private suspend fun createSimpleNotification(message: RemoteMessage){

//        val intent = Intent(this, MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
        val intent = Intent().apply {
            //flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

         val flag = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
         val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, flag)

        val bitmap: Bitmap = if(message.notification?.imageUrl != null){
            loadImage(message.notification?.imageUrl.toString())
        }else{
            BitmapFactory.decodeResource(this.resources, R.drawable.empty_data)
        }

        val builder = NotificationCompat.Builder(this, AppConstants.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(message.notification?.title ?: "")
            .setContentText(message.notification?.body ?: "")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setLargeIcon(bitmap)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
            //.setStyle(NotificationCompat.BigTextStyle().bigText(message.notification?.body ?: ""))
            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
            .setDefaults(Notification.DEFAULT_VIBRATE)
            .setContentIntent(pendingIntent)

        NotificationManagerCompat.from(this).notify(1, builder.build())
    }

    private fun createChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                AppConstants.NOTIFICATION_CHANNEL_ID,
                AppConstants.NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private suspend fun loadImage(urlImage: String): Bitmap {
        //bitmap
        val loading = ImageLoader(this)
        val request = ImageRequest.Builder(this)
            .data(urlImage)
            .build()

        val result = withContext(Dispatchers.IO) { (loading.execute(request) as SuccessResult).drawable }
        return (result as BitmapDrawable).bitmap
    }
}