package com.example.ppab_responsi1_l0122018_alyzakhoirunnadif.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.ppab_responsi1_l0122018_alyzakhoirunnadif.R
import com.example.ppab_responsi1_l0122018_alyzakhoirunnadif.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val btnGithub = root.findViewById<Button>(R.id.btn_github)
        btnGithub.setOnClickListener {
            val webpage = Uri.parse("https://github.com/Aorus22")
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            startActivity(intent)
        }

        val btnShare = root.findViewById<Button>(R.id.btn_share)
        val namaTextView = root.findViewById<TextView>(R.id.data_nama)
        val nimTextView = root.findViewById<TextView>(R.id.data_nim)
        val jurusanAngkatanTextView = root.findViewById<TextView>(R.id.data_jurusan_angkatan)
        val fakultasTextView = root.findViewById<TextView>(R.id.data_fakultas)
        val universitasTextView = root.findViewById<TextView>(R.id.data_universitas)

        val pesan = "Nama: ${namaTextView.text}\nNim: ${nimTextView.text}\nJurusan: ${jurusanAngkatanTextView.text}\nFakultas: ${fakultasTextView.text}\nUniversitas: ${universitasTextView.text}"
        btnShare.setOnClickListener {
            val implicitIntent = Intent(Intent.ACTION_SEND)
            implicitIntent.type = "text/plain"
            implicitIntent.putExtra(Intent.EXTRA_TEXT, pesan)
            val share = Intent.createChooser(implicitIntent, "Share")
            startActivity(share)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}