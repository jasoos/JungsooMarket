package com.android.jungsoomarket.zxing;

import com.android.jungsoomarket.R;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class QRCaptureActivity extends CaptureActivity {
    @Override
    protected DecoratedBarcodeView initializeContent() {
        setContentView(R.layout.capture_small);
        DecoratedBarcodeView view = findViewById(R.id.zxing_barcode_scanner);
        view.setStatusText("");
        return view;
    }
}
