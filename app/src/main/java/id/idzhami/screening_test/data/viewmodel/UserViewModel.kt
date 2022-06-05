package id.idzhami.screening_test.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import id.idzhami.screening_test.data.networks.Resource
import id.idzhami.screening_test.data.repository.UserRepository
import id.idzhami.screening_test.data.responses.UserResponses
import id.idzhami.screening_test.ui.base.BaseViewModel
import kotlinx.coroutines.launch

/**
 * Created by Usman idzhami On 04/06/2022
 */
class UserViewModel(
    private val repository: UserRepository
) : BaseViewModel(repository) {
    private val _userRespon: MutableLiveData<Resource<UserResponses>> = MutableLiveData()
    val _userResponses: LiveData<Resource<UserResponses>>
        get() = _userRespon
    
    fun GET_USER(
        page: Int,
        size: Int,
    ) = viewModelScope.launch {
        _userRespon.value = Resource.Loading
        _userRespon.value = repository.GET_USER(page, size)
    }
}