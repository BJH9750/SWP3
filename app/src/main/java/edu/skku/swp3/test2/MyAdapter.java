package edu.skku.swp3.test2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MyAdapter extends BaseAdapter implements View.OnClickListener{

    /* 아이템을 세트로 담기 위한 어레이 */
    public ArrayList<MyItem> mItems = new ArrayList<>();

    MyAdapter(){

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public MyItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();
        final ViewHolder holder;

        final int pos = position;

        /* 'listview_custom' Layout을 inflate하여 convertView 참조 획득 */
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_custom, parent, false);

            /* 'listview_custom'에 정의된 위젯에 대한 참조 획득 */
            holder = new ViewHolder();
            holder.name_tv = (TextView) convertView.findViewById(R.id.tv_place);
            holder.contents_prb = (ProgressBar) convertView.findViewById(R.id.prb_num);
            holder.place_img = (ImageView) convertView.findViewById(R.id.place_img);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        //TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name) ;
        //TextView tv_contents = (TextView) convertView.findViewById(R.id.tv_contents) ;

        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        final MyItem myItem = getItem(position);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */
        holder.name_tv.setText(myItem.getName());
        holder.contents_prb.setProgress(20/*myItem.getPeople()*/);


        /*holder.favorite_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myItem.getOrder() == 1){
                    myItem.updateOrder(0);
                    changeButton(holder.favorite_ib,R.drawable.star_empty);
                }
                else{
                    myItem.updateOrder(1);
                    changeButton(holder.favorite_ib,R.drawable.star);
                }
                Collections.sort(mItems);
                MyAdapter.this.notifyDataSetChanged();
            }
        });

        holder.delete_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItems.remove(pos);
                Collections.sort(mItems);
                MyAdapter.this.notifyDataSetChanged();
            }
        });*/
        /* (위젯에 대한 이벤트리스너를 지정하고 싶다면 여기에 작성하면된다..)  */

        return convertView;
    }

    /* 아이템 데이터 추가 함수 */
    public void addItem(String name, String code, String serial) {

        MyItem mItem = new MyItem();

        mItem.setSerial(serial);
        mItem.setName(name);
        mItem.setCode(code);

        mItems.add(mItem);

    }

    public void addItem(MyItem oneItem){
        MyItem mItem = new MyItem(oneItem);

        mItems.add(mItem);
        Collections.sort(mItems);
    }

    public void changeButton(ImageButton bnt, int resId){
        bnt.setImageResource(resId);
    }

    static class ViewHolder{
        public TextView name_tv;
        public ProgressBar contents_prb;
        public ImageView place_img;
    }


}

