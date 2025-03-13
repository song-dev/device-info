package com.song.deviceinfo.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.song.deviceinfo.databinding.FragmentAboutBinding

/**
 * Created by chensongsong on 2020/5/26.
 */
class AboutFragment : Fragment() {
    private var aboutViewModel: AboutViewModel? = null
    private var binding: FragmentAboutBinding? = null
    
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding!!.root
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        aboutViewModel =
            ViewModelProviders.of(this).get(AboutViewModel::class.java)
        aboutViewModel!!.text.observe(viewLifecycleOwner) { s -> binding!!.tvAboutVersion.text = s }
        aboutViewModel!!.setData(requireContext())
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
