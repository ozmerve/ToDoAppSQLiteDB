package com.merveoz.sqliteexample.ui.dailynotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.merveoz.sqliteexample.R
import com.merveoz.sqliteexample.common.viewBinding
import com.merveoz.sqliteexample.data.model.Note
import com.merveoz.sqliteexample.data.source.local.Database
import com.merveoz.sqliteexample.databinding.DialogAddNoteBinding
import com.merveoz.sqliteexample.databinding.FragmentDailyNotesBinding


class DailyNotesFragment : Fragment(R.layout.fragment_daily_notes), DailyNotesAdapter.DailyNotesListener {

    private val binding by viewBinding (FragmentDailyNotesBinding::bind)

    private lateinit var db : Database

    private val dailyNotesAdapter = DailyNotesAdapter(this)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = Database(requireContext())

        with(binding) {
            rvDailyNotes.adapter = dailyNotesAdapter
            dailyNotesAdapter.updateList(db.getDailyNotes())

            fabAdd.setOnClickListener {
                showAddDialog()
            }
        }
    }

    private fun showAddDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogBinding = DialogAddNoteBinding.inflate(layoutInflater) //initialize
        builder.setView(dialogBinding.root)
        val dialog = builder.create()

        with(dialogBinding){

            btnAddNote.setOnClickListener {

                val title = etTitle.text.toString()
                val description = etDesc.text.toString()
                var priorityColorId = R.color.default_priority_color

                //Öncelik rengini belirleme
                if (rbHigh.isChecked){
                    priorityColorId = R.color.highPriorityColor
                } else if (rbMedium.isChecked){
                    priorityColorId = R.color.mediumPriorityColor
                } else if (rbLow.isChecked){
                    priorityColorId = R.color.lowPriorityColor
                }


                if(title.isNotEmpty() && description.isNotEmpty()){

                    db.addDailyNote(Note(title = title, description = description, priorityColorId = priorityColorId ))
                    dailyNotesAdapter.updateList(db.getDailyNotes())
                    dialog.dismiss()
                } else {
                    Toast.makeText(requireContext(), "Please fill in the blanks!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        dialog.show()
    }


    override fun onNoteClick(description: String) {
        Toast.makeText(requireContext(), description, Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteClick(id: Int) {
        db.deleteDailyNote(id)
        dailyNotesAdapter.updateList(db.getDailyNotes())
    }
}