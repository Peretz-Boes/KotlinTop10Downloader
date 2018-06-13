package academy.learnprogramming.kotlintop10downloader

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.net.URL
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private val LOG_TAG="MainActivity"
    private val downloadData by lazy { DownloadData(this,xmlListView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        private class DownloadData(context:Context,listView: ListView): AsyncTask<String, Void, String>() {
            private val LOG_TAG="DownloadData"
            var propContext:Context by Delegates.notNull()
            var propListView:ListView by Delegates.notNull()
            init {
                propContext=context
                propListView=listView
            }
            override fun doInBackground(vararg url: String?): String {
                Log.d(LOG_TAG,"doInBackground starts with ${url[0]}")
                val rssFeed=downloadXML(url[0])
                if (rssFeed.isEmpty()){
                    Log.e(LOG_TAG,"Error downloading data")
                }
                return rssFeed
            }

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                val parseApplications=ParseApplications()
                parseApplications.parse(result)
                val arrayAdapter=ArrayAdapter<FeedEntry>(propContext,R.layout.list_item,parseApplications.applications)
                propListView.adapter=arrayAdapter
            }
            private fun downloadXML(urlPath:String?):String{
                return URL(urlPath).readText()
            }
        }
    }

}



