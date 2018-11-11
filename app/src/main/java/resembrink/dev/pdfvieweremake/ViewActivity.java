package resembrink.dev.pdfvieweremake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;

public class ViewActivity extends AppCompatActivity {

    PDFView pdfView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        pdfView=(PDFView)findViewById(R.id.pdf_viewer);
        progressBar= findViewById(R.id.progress_bar);



        if(getIntent()!= null)
        {
            String viewType = getIntent().getStringExtra("ViewType");

            if(viewType !=null || !TextUtils.isEmpty(viewType))
            {
                if(viewType.equals("assets"))
                {
                    pdfView.fromAsset("cv_data.pdf")
                    .password(null)
                    .defaultPage(0)
                    .swipeHorizontal(false)
                   // .enableDoubletap(true)
                    .onDraw(new OnDrawListener() {
                        @Override
                        public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                        }
                    })
                    .onPageError(new OnPageErrorListener() {
                        @Override
                        public void onPageError(int page, Throwable t) {
                            Toast.makeText(ViewActivity.this, "error" +page, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .onPageChange(new OnPageChangeListener() {
                        @Override
                        public void onPageChanged(int page, int pageCount) {

                        }
                    })
                    .onTap(new OnTapListener() {
                        @Override
                        public boolean onTap(MotionEvent e) {
                            return true;
                        }
                    })
                    .onRender(new OnRenderListener() {
                        @Override
                        public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                            pdfView.fitToWidth();
                        }
                    })
                    .enableAnnotationRendering(true)
                    .invalidPageColor(Color.WHITE)
                    .load();


                }



            else if(viewType.equals("storage"))
            {
                Uri pdfile= Uri.parse(getIntent().getStringExtra("FileUri"));

                pdfView.fromUri(pdfile)
                        .password(null)
                        .defaultPage(0)
                        .swipeHorizontal(false)
                        // .enableDoubletap(true)
                        .onDraw(new OnDrawListener() {
                            @Override
                            public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                            }
                        })
                        .onPageError(new OnPageErrorListener() {
                            @Override
                            public void onPageError(int page, Throwable t) {
                                Toast.makeText(ViewActivity.this, "error" +page, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onPageChange(new OnPageChangeListener() {
                            @Override
                            public void onPageChanged(int page, int pageCount) {

                            }
                        })
                        .onTap(new OnTapListener() {
                            @Override
                            public boolean onTap(MotionEvent e) {
                                return true;
                            }
                        })
                        .onRender(new OnRenderListener() {
                            @Override
                            public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                                pdfView.fitToWidth();
                            }
                        })
                        .enableAnnotationRendering(true)
                        .invalidPageColor(Color.WHITE)
                        .load();
            }

            else if (viewType.equals("internet"))
                {
                    progressBar.setVisibility(View.VISIBLE);

                    FileLoader.with(this)
                            .load("https://www.uv.es/tunon/Master_Ing_Bio/tema_1.pdf")
                            .fromDirectory("PDFiles", FileLoader.DIR_EXTERNAL_PUBLIC)
                            .asFile(new FileRequestListener<File>() {
                                @Override
                                public void onLoad(FileLoadRequest fileLoadRequest, FileResponse<File> fileResponse) {
                                    progressBar.setVisibility(View.GONE);

                                    File pdfFile= fileResponse.getBody();

                                    pdfView.fromFile(pdfFile)
                                            .password(null)
                                            .defaultPage(0)
                                            .swipeHorizontal(false)
                                            .onDraw(new OnDrawListener() {
                                                @Override
                                                public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                                                }
                                            })
                                            .onPageError(new OnPageErrorListener() {
                                                @Override
                                                public void onPageError(int page, Throwable t) {
                                                    Toast.makeText(ViewActivity.this, "error" +page, Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .onPageChange(new OnPageChangeListener() {
                                                @Override
                                                public void onPageChanged(int page, int pageCount) {

                                                }
                                            })
                                            .onTap(new OnTapListener() {
                                                @Override
                                                public boolean onTap(MotionEvent e) {
                                                    return true;
                                                }
                                            })
                                            .onRender(new OnRenderListener() {
                                                @Override
                                                public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                                                    pdfView.fitToWidth();
                                                }
                                            })
                                            .enableAnnotationRendering(true)
                                            .invalidPageColor(Color.WHITE)
                                            .load();
                                }

                                @Override
                                public void onError(FileLoadRequest fileLoadRequest, Throwable throwable) {
                                    Toast.makeText(ViewActivity.this, ""+ throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);

                                }
                            });

                }

        }

    }

    }
}
