package com.onval.bakingapp.view;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.onval.bakingapp.R;
import com.onval.bakingapp.data.Step;
import com.onval.bakingapp.utils.FormatUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer player;
    TextView title;
    TextView instruction;
    Button previousBtn, nextBtn;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);

        exoPlayerView = (SimpleExoPlayerView) root.findViewById(R.id.exoplayer_view);
        title = (TextView) root.findViewById(R.id.step_title);
        instruction = (TextView) root.findViewById(R.id.step_instruction);
        previousBtn = (Button) root.findViewById(R.id.btn_previous);
        nextBtn = (Button) root.findViewById(R.id.btn_next);

        Step step = getActivity().getIntent().getExtras().getParcelable(StepDetailFragment.STEP_INSTRUCTION_TAG);

        title.setText(step.getShortDescription());
        instruction.setText(FormatUtils.formatStepInstructions(step.getDescription()));

        Uri uri = Uri.parse(step.getVideoURL());

        if (uri.toString().equals(""))
            uri = Uri.parse("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4");

        Log.d("URI", uri.toString());

        MediaSource source = new ExtractorMediaSource(
                    uri,
                    new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "BakingRecipes")),
                    new DefaultExtractorsFactory(),
                    null,
                    null
            );

        player = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultTrackSelector());

        player.prepare(source);
        //todo: after playing switch automatically to the next step
        player.setPlayWhenReady(true);

        exoPlayerView.setPlayer(player);

        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: maybe use singleton fragment stuff to pass in the parameters?
                //todo: another solution, pass the entire steps arraylist to this fragment

                Toast.makeText(getContext(), "prev btn", Toast.LENGTH_SHORT).show();;
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "next btn", Toast.LENGTH_SHORT).show();;

            }
        });

        return root;
    }

    @Override
    public void onStop() {
        super.onStop();

        player.setPlayWhenReady(false);
        player.release();
    }
}
