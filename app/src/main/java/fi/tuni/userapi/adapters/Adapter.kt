package fi.tuni.userapi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fi.tuni.userapi.R
import fi.tuni.userapi.controllers.deleteRequest
import fi.tuni.userapi.controllers.updateRequest
import fi.tuni.userapi.models.User

class Adapter(private val userList: MutableList<User>) :
    RecyclerView.Adapter<Adapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item,
            parent, false
        )

        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentItem = userList[position]

        // add image with glide
        if (currentItem.image != null) {
            Glide.with(holder.image)
                .load(currentItem.image)
                .into(holder.image)
        }
        //add name and phone number to fields
        holder.name.text = "${currentItem.firstName} ${currentItem.lastName}"
        holder.phone.text = currentItem.phone

        // handle delete
        holder.deletebtn.setOnClickListener {
            deleteRequest(currentItem.id)

            // delete from list and notify view of deletion
            val index = userList.indexOf(currentItem)
            if (index != -1) {
                userList.removeAt(index)
                notifyItemRangeRemoved(index, 1)
            }
        }

        // handle update
        holder.updatebtn.setOnClickListener {

            // create update dialog
            val updateDialog = AlertDialog.Builder(holder.itemView.context)

            // inflate custom alertdialog view
            val updateUserView= LayoutInflater.from(holder.itemView.context).inflate(
                R.layout.update_user,
                null
            )
            // create variables from update_user.xml
            val firstName: EditText = updateUserView.findViewById(R.id.firstName)
            val lastName: EditText = updateUserView.findViewById(R.id.lastName)
            val phone: EditText = updateUserView.findViewById(R.id.phone)

            // set textfield hints as current user information
            firstName.hint = currentItem.firstName
            lastName.hint = currentItem.lastName
            phone.hint = currentItem.phone

            updateDialog.setView(updateUserView)

                // update button in update dialogue window
                .setPositiveButton("Update") { dialog, _ ->

                    val updatedFirstName = firstName.text.toString()
                    val updatedLastName = lastName.text.toString()
                    val updatedPhone = phone.text.toString()

                    // send update request
                    try {
                        updateRequest(
                            User(
                                id = currentItem.id,
                                firstName = updatedFirstName,
                                lastName = updatedLastName,
                                phone = updatedPhone
                            )
                        )
                    } catch(err:Error) {
                        print("Error when executing post request")
                    }

                    // update user information in frontend
                    currentItem.firstName =
                        updatedFirstName.ifEmpty { currentItem.firstName }

                    currentItem.lastName =
                        updatedLastName.ifEmpty { currentItem.lastName }

                    currentItem.phone =
                        updatedPhone.ifEmpty { currentItem.phone }

                    val index = userList.indexOf(currentItem)
                    notifyItemChanged(index)
                }

                // cancel button in update dialogue window
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()

            updateDialog.show()
        }
    }

    override fun getItemCount() = userList.size

    // holds variables from item.xml
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val phone: TextView = itemView.findViewById(R.id.phone)
        val image: ImageView = itemView.findViewById(R.id.image_view)
        val deletebtn: Button = itemView.findViewById(R.id.delete)
        val updatebtn: Button = itemView.findViewById(R.id.update)

    }
}