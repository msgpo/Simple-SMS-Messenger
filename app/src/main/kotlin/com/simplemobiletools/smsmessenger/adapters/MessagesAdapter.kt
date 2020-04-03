package com.simplemobiletools.smsmessenger.adapters

import android.graphics.Typeface
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.simplemobiletools.commons.adapters.MyRecyclerViewAdapter
import com.simplemobiletools.commons.extensions.formatDate
import com.simplemobiletools.commons.views.FastScroller
import com.simplemobiletools.commons.views.MyRecyclerView
import com.simplemobiletools.smsmessenger.R
import com.simplemobiletools.smsmessenger.activities.SimpleActivity
import com.simplemobiletools.smsmessenger.models.Message
import kotlinx.android.synthetic.main.item_message.view.*

class MessagesAdapter(
    activity: SimpleActivity, var messages: ArrayList<Message>,
    recyclerView: MyRecyclerView,
    fastScroller: FastScroller,
    itemClick: (Any) -> Unit
) : MyRecyclerViewAdapter(activity, recyclerView, fastScroller, itemClick) {

    init {
        setupDragListener(true)
    }

    override fun getActionMenuId() = R.menu.cab_messages

    override fun prepareActionMode(menu: Menu) {}

    override fun actionItemPressed(id: Int) {}

    override fun getSelectableItemCount() = messages.size

    override fun getIsItemSelectable(position: Int) = true

    override fun getItemSelectionKey(position: Int) = messages.getOrNull(position)?.id

    override fun getItemKeyPosition(key: Int) = messages.indexOfFirst { it.id == key }

    override fun onActionModeCreated() {}

    override fun onActionModeDestroyed() {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = createViewHolder(R.layout.item_message, parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messages[position]
        holder.bindView(message, true, true) { itemView, layoutPosition ->
            setupView(itemView, message)
        }
        bindViewHolder(holder)
    }

    override fun getItemCount() = messages.size

    private fun getItemWithKey(key: Int): Message? = messages.firstOrNull { it.id == key }

    private fun getSelectedItems() = messages.filter { selectedKeys.contains(it.id) } as ArrayList<Message>

    private fun setupView(view: View, message: Message) {
        view.apply {
            message_address.text = message.address
            message_body_short.text = message.body
            message_date.text = message.date.formatDate(context)

            if (message.read) {
                message_address.setTypeface(null, Typeface.NORMAL)
                message_body_short.setTypeface(null, Typeface.NORMAL)
                message_body_short.alpha = 0.7f
            } else {
                message_address.setTypeface(null, Typeface.BOLD)
                message_body_short.setTypeface(null, Typeface.BOLD)
                message_body_short.alpha = 1f
            }

            arrayListOf<TextView>(message_address, message_body_short).forEach {
                it.setTextColor(textColor)
            }
        }
    }
}