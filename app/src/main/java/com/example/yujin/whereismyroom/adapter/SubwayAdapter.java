package com.example.yujin.whereismyroom.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yujin.whereismyroom.R;
import com.example.yujin.whereismyroom.Subway;
import com.example.yujin.whereismyroom.common.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class SubwayAdapter extends ArrayAdapter<Subway> {
    private List<Subway> subwayListFull;

    public SubwayAdapter(Context context, String nameFilter) {
        super(context, 0);
        subwayListFull = new ArrayList<>();
    }

    @Nullable
    @Override
    public Subway getItem(int position) {
        return subwayListFull.get(position);
    }

    @Override
    public int getCount() {
        return subwayListFull.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.subway_autocomplete_row, parent, false);
        }

        LinearLayout layout = convertView.findViewById(R.id.subway_layout_route);
        TextView stationName = convertView.findViewById(R.id.subway_name);

        Subway subway = getItem(position);

        if (subway != null) {
            stationName.setText(subway.getStationName());   //역 이름

            layout.removeAllViews();    //노선 img 삭제

            //노선 img 추가
            for (int i=0; i<subway.getRouteNameList().size(); i++) {
                ImageView iv = new ImageView(getContext());
                iv.setImageResource(Util.findRouteImageResource(subway.getRouteNameList().get(i)));
                float density = getContext().getResources().getDisplayMetrics().density;
                int size = (int)(density * 25);
                int margin = (int)(density * 7);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
                params.gravity = Gravity.RIGHT;
                params.setMargins(0, 0, margin, 0);
                layout.addView(iv, params);
            }
        }

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return subwayFilter;
    }

    private Filter subwayFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            if (constraint != null) {
                List<Subway> suggestions = jsonParser(constraint.toString().trim());
                subwayListFull.clear();

                for (Subway subway : suggestions) {
                    subwayListFull.add(subway);
                }

                filterResults.values = subwayListFull;
                filterResults.count = subwayListFull.size();
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results != null && results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Subway) resultValue).getStationName();
        }
    };

    public List<Subway> jsonParser(String input) {
        List<Subway> list = new ArrayList<>();

        try {
            StringBuilder urlBuilder = new StringBuilder(getContext().getString(R.string.subwayURL));
            urlBuilder.append("?subwayStationName=" + URLEncoder.encode(input, "UTF-8"));
            urlBuilder.append("&ServiceKey=" + getContext().getString(R.string.subwayKey));
            urlBuilder.append("&_type=json");
            urlBuilder.append("&numOfRows=100");
            urlBuilder.append("&pageNo=1");

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader in = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader br = new BufferedReader(in);
                StringBuilder sb = new StringBuilder();
                String line;

                while((line = br.readLine()) != null) {
                    sb.append(line);
                }

                JSONObject object = new JSONObject(sb.toString());

                JSONObject parseResponse = (JSONObject) object.get("response");
                JSONObject parseHeader = (JSONObject) parseResponse.get("body");
                int totalCount = (int) parseHeader.get("totalCount");

                if (totalCount != 0) {
                    JSONObject parseItems = (JSONObject) parseHeader.get("items");
                    JSONArray parseItem = (JSONArray) parseItems.get("item");

                    for (int i=0; i<parseItem.length(); i++) {
                        JSONObject item = (JSONObject) parseItem.get(i);
                        String stationId = (String) item.get("subwayStationId");
                        String stationName = (String) item.get("subwayStationName");
                        String routeName = (String) item.get("subwayRouteName");

                        //TODO: 역 하나에 여러 노선인 거 처리 고민하기
                        int index = list.indexOf(new Subway(stationName));

                        if (index == -1) {
                            list.add(new Subway(stationId, stationName, routeName));
                        } else {
                            list.get(index).addRouteNameList(routeName);
                        }

                        //TODO: 지하철 노선 ImageUrl 처리 추가
//                        list.add(new Subway(stationId, stationName, routeName));
                    }
                }
                br.close();
            }

            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}
