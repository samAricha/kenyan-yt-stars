/*
 * Copyright 2023 Joel Kanyi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package di


import org.koin.core.module.Module
import org.koin.dsl.module
import presentation.home_screen.HomeViewModel
import presentation.video_list_screen.VideoListViewModel

fun commonModule() = module {

    single<HomeViewModel> {
        HomeViewModel()
    }

    // Define a factory for creating VideoListViewModel with a channelId
    factory { (channelId: String) -> VideoListViewModel(channelId) }


//    single<MainViewModel> {
//        MainViewModel(
//            settingsRepository = get(),
//        )
//    }
//
//    single<OnboadingViewModel> {
//        OnboadingViewModel(
//            settingsRepository = get(),
//        )
//    }
}

expect fun platformModule(): Module
