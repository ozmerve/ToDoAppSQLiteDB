package com.merveoz.sqliteexample.ui.dailynotes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.merveoz.sqliteexample.R
import com.merveoz.sqliteexample.data.model.Note
import com.merveoz.sqliteexample.databinding.ItemDailyNoteBinding

class DailyNotesAdapter(
    private val dailyNotesListener: DailyNotesListener

):  RecyclerView.Adapter<DailyNotesAdapter.DailyNoteViewHolder>() {

    private val noteList = mutableListOf<Note>()

    //view binding'i ayağa kaldırıp viewholder classında buluşturacak fonk
    //bindingi ayağa kaldırma. listeleme yapısı için hangi taslak tasarım kullanılacaksa onu ayağa kaldırdık
    //ve tasarımı kullanacağım class'ı ayağa kaldırdık
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyNoteViewHolder{
        val binding = ItemDailyNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DailyNoteViewHolder(binding, dailyNotesListener) //class'ı ayağa kaldırır
    }

    //yukarıdaki noteList ile aşağıdaki class DailyNoteViewHolder arasındaki bağlantıyı kurar:
    override fun onBindViewHolder(holder: DailyNoteViewHolder, position: Int){
        holder.bind(noteList[position]) //viewholder ile noteList'i bağlar

    }

    class DailyNoteViewHolder(
        private val binding: ItemDailyNoteBinding,
        private  val dailyNotesListener: DailyNotesListener
    ): RecyclerView.ViewHolder(binding.root){

        private var note: Note? = null
        private val handler = android.os.Handler()

        fun bind(note: Note){
            with(binding){
                tvTitle.text = note.title
                tvDesc.text = note.description
                tvPriorityLevel.text = getPriorityText(note.priorityColorId)

                root.setOnClickListener {
                    dailyNotesListener.onNoteClick(note.description)
                }

                cbDelete.setOnClickListener {
                    if(cbDelete.isChecked){
                        dailyNotesListener.onDeleteClick(note.id)
                    }
                }

                tvTitle.setTextColor(ContextCompat.getColor(itemView.context, note.priorityColorId))
                tvDesc.setTextColor(ContextCompat.getColor(itemView.context, note.priorityColorId))
                tvPriorityLevel.setTextColor(ContextCompat.getColor(itemView.context, note.priorityColorId))
            }
        }

        private fun getPriorityText(priorityColorId: Int): String {
            when (priorityColorId) {
                R.color.highPriorityColor -> return "High Priority"
                R.color.mediumPriorityColor -> return "Medium Priority"
                R.color.lowPriorityColor -> return "Low Priority"
                else -> return ""
            }
        }
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun updateList(list: List<Note>) {
        noteList.clear()
        noteList.addAll(list)
        notifyDataSetChanged()
    }

    interface DailyNotesListener {
        fun onNoteClick(description: String)
        fun onDeleteClick(id: Int)

    }
}