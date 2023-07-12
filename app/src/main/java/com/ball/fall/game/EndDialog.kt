package com.ball.fall.game

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.ball.fall.game.databinding.DialogBinding

class EndDialog(var score: Int): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder = AlertDialog.Builder(requireContext())
        var view = DialogBinding.inflate(layoutInflater,null,false)
        view.textView7.text = "YOUR SCORE: $score"
        builder = builder.setView(view.root)
        return builder.create()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        requireActivity().finish()
    }
}