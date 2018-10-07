package com.konkuk.dna;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.konkuk.dna.chat.ChatUser;
import com.konkuk.dna.chat.ChatUserAdapter;
import com.konkuk.dna.friend.FriendActivity;
import com.konkuk.dna.user.MyPageActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static android.view.View.GONE;

public class Helpers {
    public static void setProfile(View v) {
        // TODO 현재 유저의 정보를 초기화해줍니다.

        ImageView pfAvatar = (ImageView) v.findViewById(R.id.PfAvatar);
        TextView pfNickname = (TextView) v.findViewById(R.id.PfNickname);
        TextView pfID = (TextView) v.findViewById(R.id.PfID);

//        if (getAvatar() != null) { // TODO 현재 유저의 프로필 url이 null이 아닐 경우를 조건으로 줘야 합니다.
//            Picasso.get().load(post.getAvatar()).into(postAvatar);
//        }

        pfNickname.setText("soyoungpark");
        pfID.setText("3457soso");
    }

    public static void initDrawer(final Context context, View v, int type) {
        setProfile(v);
        LinearLayout drawerForUserList = (LinearLayout) v.findViewById(R.id.drawerForUserList);
        LinearLayout drawerForFriend = (LinearLayout) v.findViewById(R.id.drawerForFriend);

        LinearLayout myPageBtn = (LinearLayout) v.findViewById(R.id.myPageBtn);
        RelativeLayout setChatBtn = (RelativeLayout) v.findViewById(R.id.setChatBtn);
        RelativeLayout setFriendBtn = (RelativeLayout) v.findViewById(R.id.setFriendBtn);

        myPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MyPageActivity.class);
                context.startActivity(intent);
            }
        });

        setChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        setFriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FriendActivity.class);
                context.startActivity(intent);
            }
        });

        if (type == 0) {
            drawerForFriend.setVisibility(GONE);

            // TODO chatUsers 배열에 실제 접속중인 유저 리스트 추가해야 합니다.
            ListView ccuListView = (ListView) v.findViewById(R.id.ccuList);
            ArrayList<ChatUser> chatUsers = new ArrayList<ChatUser>();
            chatUsers.add(new ChatUser("3457soso", null, true));
            chatUsers.add(new ChatUser("test", null, true));
            chatUsers.add(new ChatUser("test2", null, false));
            ChatUserAdapter chatUserAdapter = new ChatUserAdapter(context, R.layout.chat_item_ccu, chatUsers);
            ccuListView.setAdapter(chatUserAdapter);
        } else if (type == 1){
            drawerForUserList.setVisibility(GONE);

            // TODO 해당 친구의 프로필을 입력해줘야 합니다.
            ImageView friendAvatar = (ImageView) v.findViewById(R.id.friendAvatar);
            TextView friendNickname = (TextView) v.findViewById(R.id.friendNickname);
            TextView friendNicknameText = (TextView) v.findViewById(R.id.friendNicknameText);
            TextView friendInfo = (TextView) v.findViewById(R.id.friendInfo);

            friendNickname.setText("fakerzzang");
            friendNicknameText.setText("fakerzzang"); // 바로 위에꺼랑 같은 값으로 세팅해주세요!
            friendInfo.setText("이번 롤드컵에 페이커가 출전하지 못해서 굉장히 유감입니다. 사실 롤을 본 지는 오래 돼서 지금 봐도 뭐가 뭔지는 모릅니다.");
        } else if (type == 2) {
            drawerForUserList.setVisibility(GONE);
            drawerForFriend.setVisibility(GONE);
        }
    }

    public static int dpToPx(Context context, int dp) {
        float density = context.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

    public static void animateListHeight(final Context context, final ListView listView, int from, int to) {
        PropertyValuesHolder topList = PropertyValuesHolder.ofInt("top", from, to);

        ValueAnimator animList = ValueAnimator.ofPropertyValuesHolder(topList);
        animList.setDuration(150L);

        ValueAnimator.AnimatorUpdateListener listUpdater = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int top = ((Integer)animation.getAnimatedValue("top")).intValue();
                listView.setTop(top);
                ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) listView.getLayoutParams();
                for (int i=1; i<=70; i++)
                    params.height = top * Helpers.dpToPx(context, i);
                listView.requestLayout();
            }
        };

        animList.addUpdateListener(listUpdater);
        animList.start();
    }
}
