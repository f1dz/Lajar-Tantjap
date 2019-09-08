package `in`.khofid.lajartantjap.services

import `in`.khofid.lajartantjap.widget.MovieRemoteViewsFactory
import android.content.Intent
import android.widget.RemoteViewsService

class MovieWidgetService: RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return MovieRemoteViewsFactory(this.applicationContext)
    }
}