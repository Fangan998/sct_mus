package com.example.sct_mus;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageView imgFront, imgStop, imgPlay, imgPause, imgNext, imgEnd;
    private ListView lstMusic;
    private TextView txtMusic;
    public MediaPlayer mediaplayer;
    private final String SONGPATH= "sdcard/";

    String[] songname=new String[]{"Find Me Here","Ice Kenkey Beat",
                                    "Lofi Mallet","Ocean View","Savior Search",
                                    "True Messiah","Wandering Soul"};

    int[] songFile= new int[]{R.raw.find_me_here,R.raw.ice_kenkey_beat,
                                R.raw.lofi_mallet,R.raw.ocean_view,R.raw.savior_search,
                                R.raw.true_messiah,R.raw.wandering_soul};

    private  int cListItem=0;
    private Boolean falgPause;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ImgView
        imgEnd=(ImageView) findViewById(R.id.imgEnd);
        imgFront=(ImageView)findViewById(R.id.imgFront);
        imgNext=(ImageView)findViewById(R.id.imgNext);
        imgPause=(ImageView) findViewById(R.id.imgPause);
        imgPlay=(ImageView)findViewById(R.id.imgPlay);
        imgStop=(ImageView)findViewById(R.id.imgStop);
        //ListView
        lstMusic=(ListView)findViewById(R.id.lstMusic);
        //TxtShow
        txtMusic=(TextView)findViewById(R.id.txtSong2);

        //img listener
        imgStop.setOnClickListener(ImgListener);
        imgPlay.setOnClickListener(ImgListener);
        imgPause.setOnClickListener(ImgListener);
        imgNext.setOnClickListener(ImgListener);
        imgFront.setOnClickListener(ImgListener);
        imgEnd.setOnClickListener(ImgListener);
        //list listenero
        lstMusic.setOnItemClickListener(LstListener);

        mediaplayer=new MediaPlayer();

        ArrayAdapter<String> adaSong=new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,songname);
        lstMusic.setAdapter(adaSong);

    }

    ImageView.OnClickListener ImgListener = new ImageView.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imgPlay:{
                    if(falgPause) {  //如果是暫停狀態就繼續播放
                        mediaplayer.start();
                        falgPause=false;
                    } else  //非暫停則重新播放
                        playSong(songFile[cListItem]);
                    break;
                }
                case R.id.imgStop:{
                    if(mediaplayer.isPlaying()){
                        mediaplayer.release();
                    }
                    break;
                }
                case R.id.imgFront:{
                    frontSong();
                    break;
                }
                case R.id.imgNext:{
                    nextSong();
                    break;
                }
                case R.id.imgPause:{
                    mediaplayer.pause();
                    falgPause=true;
                    break;
                }
                case R.id.imgEnd:{
                    mediaplayer.release();
                    finish();
                    break;
                }

            }
        }
    };

    private ListView.OnItemClickListener LstListener = new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            cListItem = position;
            playSong(songFile[cListItem]);
        }
    };

    private void playSong(int song) {
        mediaplayer.reset();
        mediaplayer= MediaPlayer.create(MainActivity.this, song); //播放歌曲源
        mediaplayer.start(); //開始播放
        txtMusic.setText("歌名：" + songname[cListItem]); //更新歌名
        mediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer arg0) {
                nextSong(); //播放完後播下一首
            }
        });
        falgPause=false;
    }

    private void nextSong() {
        cListItem++;
        if (cListItem >= lstMusic.getCount()) //若到最後就移到第一首
            cListItem = 0;
        playSong(songFile[cListItem]);
    }

    private void frontSong() {
        cListItem--;
        if (cListItem < 0)
            cListItem = lstMusic.getCount()-1; //若到第一首就移到最後
        playSong(songFile[cListItem]);
    }

}