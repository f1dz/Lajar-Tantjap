package `in`.khofid.lajartantjap.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class Network {

    companion object {
        fun internetAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            return activeNetwork?.isConnected ?: false
        }
    }
}