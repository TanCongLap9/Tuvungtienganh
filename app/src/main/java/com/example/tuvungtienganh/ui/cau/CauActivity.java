package com.example.tuvungtienganh.ui.cau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tuvungtienganh.R;
import com.example.tuvungtienganh.models.CauModel;
import com.example.tuvungtienganh.utils.Sql;

import java.util.Locale;
import java.util.UUID;

public class CauActivity extends AppCompatActivity {
    public static final int MA_YEU_CAU_GHI_AM = 1;
    public static final String PHAN_BO_SUNG_CAU = "CAU";
    private ImageButton mNutTapNoi;
    private ImageButton mNutDoc;
    private ImageButton mNutThich;
    private TextView mCauTiengAnh;
    private TextView mCauTiengViet;
    private EditText mVanBanDuocChuyenTheTuTrinhNhanDienGiongNoi;
    private TextToSpeech mTrinhChuyenVanBanThanhGiongNoi;
    private SpeechRecognizer mTrinhNhanDienGiongNoi;
    private Toolbar mToolbar;
    private CauModel mCauModel;
    private Sql mSql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cau);
        bindViews();
        mCauModel = (CauModel)getIntent().getSerializableExtra(PHAN_BO_SUNG_CAU);
        mSql = new Sql(this, Sql.DATABASE_NAME);
        mSql.themCauNayVaoLichSuTraCuu(mCauModel.getMaDinhDanhCuaChuDe(), mCauModel.getMaDinhDanh());
        setTitle(mCauModel.getCauTiengAnh());
        mCauTiengAnh.setText(mCauModel.getCauTiengAnh());
        mCauTiengViet.setText(mCauModel.getCauTiengViet());
        capNhatNutThich();
        bindActionBar();

        mTrinhNhanDienGiongNoi = SpeechRecognizer.createSpeechRecognizer(this);
        mTrinhChuyenVanBanThanhGiongNoi = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS)
                    mTrinhChuyenVanBanThanhGiongNoi.setLanguage(Locale.ENGLISH);
                else
                    Toast.makeText(CauActivity.this, "Không thể khởi động trình đọc.", Toast.LENGTH_SHORT).show();
            }
        });
        bindEvents();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void bindActionBar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void nhanDienGiongNoi() {
        if (!SpeechRecognizer.isRecognitionAvailable(getApplicationContext())) {
            Toast.makeText(
                    getApplicationContext(),
                    "Vui lòng cài đặt trình nhận diện giọng nói để sử dụng tính năng này.",
                    Toast.LENGTH_LONG).show();
            return;
        }
        Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.setAction(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        speechIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        mTrinhNhanDienGiongNoi.setRecognitionListener(new TrinhLangNgheGiongNoi(this, mCauModel, mNutTapNoi, mVanBanDuocChuyenTheTuTrinhNhanDienGiongNoi));
        mTrinhNhanDienGiongNoi.startListening(speechIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MA_YEU_CAU_GHI_AM) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                nhanDienGiongNoi();
            else
                Toast.makeText(this, "Bạn cần cấp quyền ghi âm để sử dụng tính năng này.", Toast.LENGTH_SHORT).show();
        }
    }

    public void bindViews() {
        mNutTapNoi = findViewById(R.id.activity_cau_ghi_am);
        mNutDoc = findViewById(R.id.activity_cau_doc);
        mVanBanDuocChuyenTheTuTrinhNhanDienGiongNoi = findViewById(R.id.activity_cau_van_ban_duoc_chuyen_the);
        mCauTiengViet = findViewById(R.id.activity_cau_tieng_viet);
        mCauTiengAnh = findViewById(R.id.activity_cau_tieng_anh);
        mToolbar = findViewById(R.id.activity_cau_toolbar);
        mNutThich = findViewById(R.id.activity_cau_thich_cau);
    }

    public void bindEvents() {
        mNutDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mTrinhChuyenVanBanThanhGiongNoi.isSpeaking()) {
                    UUID utteranceId = UUID.randomUUID();
                    mTrinhChuyenVanBanThanhGiongNoi.speak(mCauModel.getCauTiengAnh(), TextToSpeech.QUEUE_FLUSH, null, utteranceId.toString());
                }
                else {
                    mTrinhChuyenVanBanThanhGiongNoi.stop();
                }
            }
        });
        mNutTapNoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[] {Manifest.permission.RECORD_AUDIO}, MA_YEU_CAU_GHI_AM);
                    return;
                }
                nhanDienGiongNoi();
            }
        });
        mNutThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSql.coThichHong(mCauModel))
                    mSql.hongThichCau(mCauModel);
                else
                    mSql.thichCau(mCauModel);
                capNhatNutThich();
            }
        });
        mTrinhChuyenVanBanThanhGiongNoi.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
                mNutDoc.post(new Runnable() {
                    @Override
                    public void run() {
                        mNutDoc.setImageResource(R.drawable.baseline_stop_24);
                    }
                });
            }

            @Override
            public void onDone(String utteranceId) {
                mNutDoc.post(new Runnable() {
                    @Override
                    public void run() {
                        mNutDoc.setImageResource(R.drawable.baseline_volume_up_24);
                    }
                });
            }

            @Override
            public void onError(String utteranceId) {

            }

            @Override
            public void onStop(String utteranceId, boolean interrupted) {
                mNutDoc.post(new Runnable() {
                    @Override
                    public void run() {
                        mNutDoc.setImageResource(R.drawable.baseline_volume_up_24);
                    }
                });
            }
        });
    }

    private void capNhatNutThich() {
        mNutThich.setImageResource(mSql.coThichHong(mCauModel)
                ? R.drawable.baseline_favorite_24
                : R.drawable.baseline_favorite_border_24);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTrinhNhanDienGiongNoi != null) {
            mTrinhNhanDienGiongNoi.stopListening();
            mTrinhNhanDienGiongNoi.cancel();
            try {
                mTrinhNhanDienGiongNoi.destroy();
            }
            catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        mTrinhNhanDienGiongNoi = null;
        if (mSql != null) mSql.close();
    }
}