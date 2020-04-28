package com.gzeinnumer.daggerpractice.di.main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import com.gzeinnumer.daggerpractice.ui.main.profile.ProfileFragment;

@Module
public abstract class MainFragmentBuilderModule {
    @ContributesAndroidInjector
    abstract ProfileFragment constributeProfileFragment();
}
