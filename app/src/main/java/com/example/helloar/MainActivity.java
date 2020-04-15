package com.example.helloar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivity extends AppCompatActivity {
private ArFragment arFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arFragment=(ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {

            Anchor anchor=hitResult.createAnchor();
            ModelRenderable.builder().setSource(this, Uri.parse("ArcticFox_Posed.sfb")).build()
                    .thenAccept(modelRenderable -> addModelToscene(anchor,modelRenderable))
                    .exceptionally(throwable -> {
                        AlertDialog.Builder builder=new AlertDialog.Builder(this);
                        builder.setMessage(throwable.getMessage()).show();
                        return null;
                    });

        });
    }

    private void addModelToscene(Anchor anchor, ModelRenderable modelRenderable) {
        AnchorNode anchornode= new AnchorNode(anchor);
        TransformableNode trans= new TransformableNode(arFragment.getTransformationSystem());
        trans.setParent(anchornode);
        trans.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchornode);
        trans.select();
    }
}
