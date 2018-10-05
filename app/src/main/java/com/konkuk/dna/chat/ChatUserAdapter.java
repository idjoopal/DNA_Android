package com.konkuk.dna.chat;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.konkuk.dna.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatUserAdapter extends ArrayAdapter<ChatUser> {
    Context context;
    ArrayList<ChatUser> users;

    private static Typeface chatTypeface;
    private static Typeface boldTypeface;
    private static Typeface fontAwesomeS;

    public ChatUserAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ChatUser> objects) {
        super(context, resource, objects);

        this.context = context;
        this.users = objects;

        init();
    }

    public void init() {
        if(chatTypeface == null) {
            chatTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/NanumSquareR.ttf");
        }
        if(boldTypeface == null) {
            boldTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/NanumSquareB.ttf");
        }
        if(fontAwesomeS == null) {
            fontAwesomeS = Typeface.createFromAsset(context.getAssets(), "fonts/fa-solid-900.ttf");
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View v, @NonNull ViewGroup parent) {
        ChatUser user = users.get(position);

        if (v == null) {
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(R.layout.chat_item_ccu, null);
        }

        ImageView avatar = v.findViewById(R.id.ccuAvatar);
        TextView nickname = v.findViewById(R.id.ccuNickname);
        TextView status = v.findViewById(R.id.ccuStatus);
        ImageButton addFriendBtn = v.findViewById(R.id.addFriendBtn);

        if (user.getAvatar() != null) {
            Picasso.get().load(user.getAvatar()).into(avatar);
        }

        nickname.setText(user.getNickname());
        status.setTypeface(fontAwesomeS);
        if (user.getInside()) { // 초록불 켜기!
            status.setTextColor(context.getResources().getColor(R.color.green));
        } else {
            status.setTextColor(context.getResources().getColor(R.color.red));
        }

        // TODO 해당 유저가 나와 친구 관계인지 아닌지 확인하고, 친구 추가 버튼을 보여줍니다.
        if (position % 2 == 0) { // TODO 여기에 [친구 관계가 아닌] 조건을 추가해주면 됩니다.
            addFriendBtn.setVisibility(View.GONE);
        } else {
            addFriendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO 친구 버튼이 눌렸을 경우
                }
            });
        }

        return v;
    }
}

