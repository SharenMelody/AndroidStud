package com.example.ourbook

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class Adapter(private var book: List<Book>, context: Context): RecyclerView.Adapter<Adapter.BookViewHolder>() {

    private val db: DatabaseHelper = DatabaseHelper(context)

    class BookViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nama : TextView = itemView.findViewById(R.id.namaJudul)
        val alias : TextView = itemView.findViewById(R.id.textAlias)
        val email : TextView = itemView.findViewById(R.id.textEmail)
        val alamat : TextView = itemView.findViewById(R.id.textAlamat)
        val tglLahir : TextView = itemView.findViewById(R.id.textTglahir)
        val hp : TextView = itemView.findViewById(R.id.textNoHp)
        val foto : ImageView = itemView.findViewById(R.id.imageProfile)
        val updateButton : ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton : ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        return BookViewHolder(view)
    }

    override fun getItemCount(): Int = book.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = book[position]
        holder.nama.text = book.name
        holder.alias.text = book.surename
        holder.email.text = book.email
        holder.alamat.text = book.address
        holder.tglLahir.text = book.date
        holder.hp.text = book.hp

        if (book.image != null) {
            val bmp = BitmapFactory.decodeByteArray(book.image, 0, book.image.size)
            holder.foto.setImageBitmap(bmp)
        } else {
            holder.foto.setImageResource(R.drawable.baseline_image_24)
        }

        holder.updateButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, editbook::class.java).apply {
                putExtra("book_id", book.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            db.deleteBook(book.id)
            refreshData(db.getAllBooks())
            Toast.makeText(holder.itemView.context, "Note Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newBooks : List<Book>) {
        book = newBooks
        notifyDataSetChanged()
    }
}