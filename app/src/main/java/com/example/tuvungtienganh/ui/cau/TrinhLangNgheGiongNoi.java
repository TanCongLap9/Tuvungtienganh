package com.example.tuvungtienganh.ui.cau;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tuvungtienganh.R;
import com.example.tuvungtienganh.models.CauModel;

import java.util.regex.Pattern;

public class TrinhLangNgheGiongNoi implements RecognitionListener {
    private Context mContext;
    private CauModel mCau;
    private ImageButton mNutDoc;
    private EditText mVanBanDuocChuyenTheTuTrinhNhanDienGiongNoi;

    public TrinhLangNgheGiongNoi(Context context, CauModel cau, ImageButton nutDoc, EditText vanBanDuocChuyenTheTuTrinhNhanDienGiongNoi) {
        this.mContext = context;
        this.mCau = cau;
        this.mNutDoc = nutDoc;
        this.mVanBanDuocChuyenTheTuTrinhNhanDienGiongNoi = vanBanDuocChuyenTheTuTrinhNhanDienGiongNoi;
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        mNutDoc.setImageResource(R.drawable.baseline_more_horiz_24);
        mVanBanDuocChuyenTheTuTrinhNhanDienGiongNoi.setText("");
        mVanBanDuocChuyenTheTuTrinhNhanDienGiongNoi.setHint("Nói câu tiếng Anh ở trên đi.");
        mVanBanDuocChuyenTheTuTrinhNhanDienGiongNoi.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.baseline_circle_24, 0, 0, 0);
        mVanBanDuocChuyenTheTuTrinhNhanDienGiongNoi.setCompoundDrawableTintList(ColorStateList.valueOf(0xff000000));
    }

    @Override
    public void onBeginningOfSpeech() {
        mVanBanDuocChuyenTheTuTrinhNhanDienGiongNoi.setText("");
        mVanBanDuocChuyenTheTuTrinhNhanDienGiongNoi.setHint("Đang nghe...");
        mVanBanDuocChuyenTheTuTrinhNhanDienGiongNoi.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.baseline_circle_24, 0, 0, 0);
        mVanBanDuocChuyenTheTuTrinhNhanDienGiongNoi.setCompoundDrawableTintList(ColorStateList.valueOf(0xff000000));
    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int error) {
        mNutDoc.setImageResource(R.drawable.baseline_mic_24);
        mVanBanDuocChuyenTheTuTrinhNhanDienGiongNoi.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.baseline_circle_24, 0, 0, 0);
        mVanBanDuocChuyenTheTuTrinhNhanDienGiongNoi.setCompoundDrawableTintList(ColorStateList.valueOf(0xff000000));
        if (error == SpeechRecognizer.ERROR_SPEECH_TIMEOUT) {
            mVanBanDuocChuyenTheTuTrinhNhanDienGiongNoi.setText("");
            mVanBanDuocChuyenTheTuTrinhNhanDienGiongNoi.setHint("");
        }
        else if (error == SpeechRecognizer.ERROR_NO_MATCH) {
            mVanBanDuocChuyenTheTuTrinhNhanDienGiongNoi.setText("");
            mVanBanDuocChuyenTheTuTrinhNhanDienGiongNoi.setHint("Nghe không rõ lắm.");
        }
        switch (error) {
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                Toast.makeText(mContext, "Không có kết nối internet.", Toast.LENGTH_SHORT).show();
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                Toast.makeText(mContext, "Không có kết nối internet.", Toast.LENGTH_SHORT).show();
                break;
            case SpeechRecognizer.ERROR_AUDIO:
                Toast.makeText(mContext, "Lỗi âm thanh.", Toast.LENGTH_SHORT).show();
                break;
            case SpeechRecognizer.ERROR_SERVER:
                Toast.makeText(mContext, "Lỗi máy chủ.", Toast.LENGTH_SHORT).show();
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                Toast.makeText(mContext, "Lỗi máy khách.", Toast.LENGTH_SHORT).show();
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                Toast.makeText(mContext, "Trình nhận diện giọng nói đang bận.", Toast.LENGTH_SHORT).show();
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                Toast.makeText(mContext, "Không có đủ quyền.", Toast.LENGTH_SHORT).show();
                break;
            case SpeechRecognizer.ERROR_TOO_MANY_REQUESTS:
                Toast.makeText(mContext, "Quá nhiều yêu cầu được gửi.", Toast.LENGTH_SHORT).show();
                break;
            case SpeechRecognizer.ERROR_SERVER_DISCONNECTED:
                Toast.makeText(mContext, "Máy chủ đã ngắt kết nối.", Toast.LENGTH_SHORT).show();
                break;
            case SpeechRecognizer.ERROR_LANGUAGE_NOT_SUPPORTED:
                Toast.makeText(mContext, "Ngôn ngữ không được hỗ trợ.", Toast.LENGTH_SHORT).show();
                break;
            case SpeechRecognizer.ERROR_LANGUAGE_UNAVAILABLE:
                Toast.makeText(mContext, "Ngôn ngữ không có sẵn.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onResults(Bundle results) {
        String resultText = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0);
        mNutDoc.setImageResource(R.drawable.baseline_mic_24);
        mVanBanDuocChuyenTheTuTrinhNhanDienGiongNoi.setText(resultText);
        Pattern patTiengAnh = Pattern.compile("[^a-zA-Z0-9 ]");
        String repTiengAnh = patTiengAnh.matcher(mCau.getCauTiengAnh()).replaceAll("").toLowerCase();
        String repResultText = patTiengAnh.matcher(resultText).replaceAll("").toLowerCase();
        if (repTiengAnh.equals(repResultText)) {
            mVanBanDuocChuyenTheTuTrinhNhanDienGiongNoi.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.outline_check_circle_24, 0, 0, 0);
            mVanBanDuocChuyenTheTuTrinhNhanDienGiongNoi.setCompoundDrawableTintList(ColorStateList.valueOf(0xff00ff00));
        }
        else {
            mVanBanDuocChuyenTheTuTrinhNhanDienGiongNoi.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.outline_cancel_24, 0, 0, 0);
            mVanBanDuocChuyenTheTuTrinhNhanDienGiongNoi.setCompoundDrawableTintList(ColorStateList.valueOf(0xffff0000));
        }
    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }
}
