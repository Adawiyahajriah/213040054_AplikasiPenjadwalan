package id.ac.unpas.aplikasipenjadwalan

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import id.ac.unpas.aplikasipenjadwalan.data.AppDatabase
import id.ac.unpas.aplikasipenjadwalan.data.entity.User

class EditorActivity : AppCompatActivity() {
    private lateinit var mata_kuliahEditText: EditText
    private lateinit var nama_dosenEditText: EditText
    private lateinit var waktuEditText: EditText
    private lateinit var ruanganEditText: EditText
    private lateinit var btnSave: Button
    private lateinit var database: AppDatabase

    private var userId: Int = -1 // Default value

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)

        mata_kuliahEditText = findViewById(R.id.mata_kuliah)
        nama_dosenEditText = findViewById(R.id.nama_dosen)
        waktuEditText = findViewById(R.id.waktu)
        ruanganEditText = findViewById(R.id.ruangan)
        btnSave = findViewById(R.id.btn_save)
        database = AppDatabase.getInstance(applicationContext)

        userId = intent.getIntExtra("USER_ID", -1)

        if (userId != -1) {
            val user = database.userDao().loadById(userId)
            if (user != null) {
                mata_kuliahEditText.setText(user.mata_kuliah)
                nama_dosenEditText.setText(user.nama_dosen)
                waktuEditText.setText(user.waktu)
                ruanganEditText.setText(user.ruangan)
            } else {
                Toast.makeText(
                    applicationContext,
                    "User tidak ditemukan",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Tidak dapat menemukan data pengguna yang akan diedit",
                Toast.LENGTH_SHORT
            ).show()
        }

        btnSave.setOnClickListener {
            val mata_kuliah = mata_kuliahEditText.text.toString()
            val nama_dosen = nama_dosenEditText.text.toString()
            val waktu = waktuEditText.text.toString()
            val ruangan = ruanganEditText.text.toString()

            if (mata_kuliah.isNotEmpty() && nama_dosen.isNotEmpty() && waktu.isNotEmpty() && ruangan.isNotEmpty()) {
                val user = User()
                if (userId != -1) {
                    database.userDao().update(user)
                    Toast.makeText(
                        applicationContext,
                        "Data berhasil diupdate",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    database.userDao().insertAll(user)
                    Toast.makeText(
                        applicationContext,
                        "Data berhasil disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                finish() // Selesai setelah data disimpan/diupdate
            } else {
                Toast.makeText(
                    applicationContext,
                    "Silahkan isi semua data dengan valid",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
