package fi.tuni.userapi.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fi.tuni.userapi.R
import fi.tuni.userapi.models.User

class Adapter(private val userList: MutableList<User>) :
    RecyclerView.Adapter<Adapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item,
            parent, false)

        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentItem = userList[position]

        if (currentItem.image != null) {
            Glide.with(holder.image)
                .load(currentItem.image)
                .into(holder.image);
        }
        holder.name.text = "${currentItem.firstName} ${currentItem.lastName}"
        holder.phone.text = currentItem.phone


    }

    override fun getItemCount() = userList.size

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val phone: TextView = itemView.findViewById(R.id.phone)
        val image: ImageView = itemView.findViewById(R.id.image_view)
    }
}