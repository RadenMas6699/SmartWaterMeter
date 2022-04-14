/*
 * Created by RadenMas on 10/3/2022.
 * Copyright (c) 2022.
 */

package com.radenmas.smart.water.meter.ui.user

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.radenmas.smart.water.meter.R
import com.radenmas.smart.water.meter.databinding.FragmentChangeProfileUserBinding
import com.radenmas.smart.water.meter.model.DefaultResponse
import com.radenmas.smart.water.meter.network.Retro
import com.radenmas.smart.water.meter.utils.Utils
import com.radenmas.smart.water.meter.utils.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserChangeProfileFragment : Fragment() {
    private lateinit var b: FragmentChangeProfileUserBinding
    private lateinit var progress: Dialog

    private lateinit var sharedPref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private val args: UserChangeProfileFragmentArgs by navArgs()

    private val RESULT_OK = -1
    private var filePath: Uri? = null

    var idPelanggan: String? = null
    var avatar: String? = null
    var fullName: String? = null
    var phone: String? = null
    var address: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentChangeProfileUserBinding.inflate(layoutInflater, container, false)
        val v = b.root

        sharedPref = activity?.getSharedPreferences(
            Constant.app_pref, Context.MODE_PRIVATE
        )!!
        editor = sharedPref.edit()

        onClick()

        idPelanggan = args.idPelanggan
        avatar = args.image
        fullName = args.fullName
        phone = args.phone
        address = args.address

        if (avatar == Constant.default) {
            Glide.with(this)
                .load(R.drawable.ic_profile_default)
                .into(b.imgProfile)
        } else {
            Glide.with(this)
                .load(avatar)
                .into(b.imgProfile)
        }
        b.etFullName.hint = fullName
        b.etPhone.hint = phone
        b.etAddress.hint = address

        return v
    }

    private fun onClick() {
        b.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        b.btnSaveProfile.setOnClickListener {
            val fullname: String = b.etFullName.text.toString()
            val phone: String = b.etPhone.text.toString()
            val address: String = b.etAddress.text.toString()

            if (fullname.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Utils.toast(requireContext(), "Lengkapi yang masih kosong")
            } else {
                progress = Dialog(requireActivity())
                progress.setContentView(R.layout.dialog_progress)
                progress.window!!.setBackgroundDrawableResource(R.drawable.bg_progress)
                progress.show()

                updateProfile(args.idPelanggan, fullname, phone, address)
            }
        }

        b.imgChangeProfile.setOnClickListener {
            chooseFoto()
        }
    }

    private fun chooseFoto() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), 71)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 71 && resultCode == RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            UploadFoto()
        }
    }

    private fun UploadFoto() {
        Utils.showLoading(requireContext())
        val storageReference =
            FirebaseStorage.getInstance().getReference("User").child(idPelanggan.toString())
        if (filePath != null) {

            val ref = storageReference.child(idPelanggan.toString())
            ref.putFile(filePath!!)
                .addOnSuccessListener {
                    ref.downloadUrl
                        .addOnSuccessListener { uri: Uri ->
                            Retro.instance.updateAvatarUser(idPelanggan.toString(), uri.toString())
                                .enqueue(object : Callback<DefaultResponse> {
                                    override fun onResponse(
                                        call: Call<DefaultResponse>,
                                        response: Response<DefaultResponse>
                                    ) {
                                        Utils.dismissLoading()
                                        Utils.toast(
                                            requireContext(), "Foto profil berhasil diubah")

                                        Glide.with(requireContext())
                                            .load(uri.toString())
                                            .into(b.imgProfile)

                                        editor.putString(Constant.data_avatar, uri.toString())
                                        editor.apply()
                                    }

                                    override fun onFailure(
                                        call: Call<DefaultResponse>,
                                        t: Throwable
                                    ) {
                                        Utils.toast(requireContext(), "Foto profil gagal diubah")
                                    }

                                })
                        }
                }
                .addOnFailureListener { e: Exception ->
                    Utils.toast(requireContext(), "Foto profil gagal diubah")
                }
        } else {
            Utils.toast(requireContext(), "Gambar belum dipilih")
        }
    }


    private fun updateProfile(
        idPelanggan: String,
        fullname: String,
        phone: String,
        address: String
    ) {
        Retro.instance.updateDataUser(
            idPelanggan, fullname, phone, address
        ).enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                saveData(
                    fullname,
                    phone,
                    address
                )
                progress.dismiss()
                Utils.toast(requireContext(), "Data Pelanggan Berhasil Diupdate")
                activity?.onBackPressed()
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                progress.dismiss()
                Utils.toast(requireContext(), "Data Pelanggan Gagal Diupdate")
            }

        })
    }

    private fun saveData(fullname: String, phone: String, address: String) {
        editor.putString(Constant.data_name, fullname)
        editor.putString(Constant.data_phone, phone)
        editor.putString(Constant.data_address, address)

        editor.apply()
    }


}