package id.idzhami.screening_test.ui.base

import androidx.lifecycle.ViewModel
import id.idzhami.screening_test.data.repository.UserRepository
/**
 * Created by Usman idzhami On 04/06/2022
 */
abstract class  BaseViewModel(
    private val repository: UserRepository
) : ViewModel(){

}