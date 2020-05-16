package com.gzeinnumer.daggerpractice.ui.main.post;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.gzeinnumer.daggerpractice.SessionManager;
import com.gzeinnumer.daggerpractice.network.mainApi.MainApi;
import com.gzeinnumer.daggerpractice.network.mainApi.model.ResponsePost;
import com.gzeinnumer.daggerpractice.ui.main.MainResource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PostVM extends ViewModel {
    private static final String TAG = "PostVM";
    private final SessionManager sessionManager;
    private final MainApi mainApi;
    @Inject
    PostVM(SessionManager sessionManager, MainApi mainApi){
        this.mainApi = mainApi;
        this.sessionManager = sessionManager;
        Log.d(TAG, "PostVM: ready");
    }

    private MediatorLiveData<MainResource<List<ResponsePost>>> posts;
    LiveData<MainResource<List<ResponsePost>>> observePosts(){
        if(posts == null){
            posts = new MediatorLiveData<>();
            posts.setValue(MainResource.loading((List<ResponsePost>) null));
            final LiveData<MainResource<List<ResponsePost>>> source = LiveDataReactiveStreams.fromPublisher(
                    mainApi.getPotsFromUser(sessionManager.getAuthUser().getValue().data.getId())
                    .onErrorReturn(new Function<Throwable, List<ResponsePost>>() {
                        @Override
                        public List<ResponsePost> apply(Throwable throwable) throws Exception {
                            Log.d(TAG, "apply: ", throwable);
                            ResponsePost responsePost = new ResponsePost();
                            responsePost.setId(-1);
                            ArrayList<ResponsePost> p = new ArrayList<>();
                            p.add(responsePost);
                            return p;
                        }
                    })
                    .map(new Function<List<ResponsePost>, MainResource<List<ResponsePost>>>() {
                        @Override
                        public MainResource<List<ResponsePost>> apply(List<ResponsePost> responsePosts) throws Exception {
                            if(responsePosts.size() > 0){
                                if(responsePosts.get(0).getId() == -1){
                                    return MainResource.error("Ada yang salah", null);
                                }
                            }
                            return MainResource.success(responsePosts);
                        }
                    })
                    .subscribeOn(Schedulers.io())
            );
            posts.addSource(source, new Observer<MainResource<List<ResponsePost>>>() {
                @Override
                public void onChanged(MainResource<List<ResponsePost>> listMainResource) {
                    posts.setValue(listMainResource);
                    posts.removeSource(source);
                }
            });
        }
        return posts;
    }
}
