package com.irfan.nanamyuk.ui.login

import androidx.lifecycle.*
import com.irfan.nanamyuk.data.api.AuthResponse
import com.irfan.nanamyuk.data.api.ConfigApi
import com.irfan.nanamyuk.data.datastore.SessionModel
import com.irfan.nanamyuk.data.datastore.SessionPreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: SessionPreferences) : ViewModel() {
    private val _login = MutableLiveData<AuthResponse>()
    val login : LiveData<AuthResponse> = _login

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _state = MutableLiveData<Boolean>()
    val state: LiveData<Boolean> = _state

    fun getUserToken(): LiveData<SessionModel> {
        return pref.getToken().asLiveData()
    }

    fun postLogin(map : HashMap<String, String>) {
        _isLoading.value = true
        _state.value = false

        val client = ConfigApi.getApiService().postLogin(map)
        client.enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _state.value = true

                    viewModelScope.launch {
                        val token = response.body()?.token
                        val name = response.body()?.user?.firstName
                        val id = response.body()?.userId

                        if (token != null && name != null && id != null) {
                            pref.login(token, name, id)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                _state.value = false
            }
        })
    }
}