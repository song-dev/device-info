package com.song.deviceinfo.ui.applist

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.song.deviceinfo.R
import com.song.deviceinfo.databinding.ItemApplistBinding
import com.song.deviceinfo.model.beans.ApplicationBean
import com.song.deviceinfo.ui.applist.AppListAdapter.AppListHolder
import com.song.deviceinfo.ui.base.BaseAdapter

/**
 * Created by chensongsong on 2020/6/3.
 */
class AppListAdapter(context: Context) : BaseAdapter<ApplicationBean, AppListHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppListHolder {
        return AppListHolder(ItemApplistBinding.inflate(LayoutInflater.from(context), parent, false))
    }
    
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AppListHolder, position: Int) {
        if (position % 2 == 0) {
            holder.binding.root.setBackgroundColor(context.resources.getColor(R.color.color_white))
        } else {
            holder.binding.root.setBackgroundColor(context.resources.getColor(R.color.color_E8E8E8))
        }
        val bean = items[position]
        holder.binding.ivApplistIcon.setImageDrawable(bean.icon)
        holder.binding.tvApplistName.text = bean.name
        holder.binding.tvApplistPackagename.text = bean.packageName
        holder.binding.tvApplistVersion.text = bean.version + " sdk" + bean.buildVersion
        holder.binding.root.setOnClickListener { view: View? ->
            val intent = Intent()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS")
            intent.setData(Uri.fromParts("package", bean.packageName, null))
            context.startActivity(intent)
        }
    }
    
    class AppListHolder(var binding: ItemApplistBinding) : RecyclerView.ViewHolder(binding.root)
}
