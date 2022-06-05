package id.idzhami.screening_test.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.idzhami.screening_test.data.repository.BaseRepository
import id.idzhami.screening_test.data.repository.UserRepository
import id.idzhami.screening_test.data.viewmodel.UserViewModel
import java.lang.IllegalArgumentException
/**
 * Created by Usman idzhami On 04/06/2022
 */
class ViewModelFactory(
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            /* UserRepository */
            modelClass.isAssignableFrom(UserViewModel::class.java) -> UserViewModel(repository as UserRepository) as T
            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }
}