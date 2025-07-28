package com.example.gearnix.viewmodel



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gearnix.model.UserModel
import com.example.gearnix.repository.UserRepository
import com.google.firebase.auth.FirebaseUser

class UserViewModel(val repo : UserRepository) : ViewModel() {

    fun login(email : String, password : String, callback : (Boolean, String) -> Unit) {
        repo.login(email, password, callback)
    }
    fun register(email : String, password : String, callback : (Boolean, String, String) -> Unit) {
        repo.register(email, password, callback)
    }
    fun forgetPassword(email : String, callback : (Boolean, String) ->Unit) {
        repo.forgetPassword(email, callback)
    }
    fun getCurrentUser() : FirebaseUser? {
        return repo.getCurrentUser()
    }
    fun addUserToDatabase(userID : String, model: UserModel, callback: (Boolean, String) ->Unit) {
        repo.addUserToDatabase(userID, model, callback)
    }
    fun logout(callback : (Boolean, String) ->Unit) {
        repo.logout(callback)
    }

    private var _users = MutableLiveData<UserModel?>()
    val users : LiveData<UserModel?> get() = _users


    fun getUserByID(userID : String) {
        repo.getUserByID(userID) { users, success, message ->
            if(success && users != null) {
                _users.postValue(users)
            }
            else {
                _users.postValue(null)
            }
        }
    }

    fun updateProfile(userID : String, userData: MutableMap<String, Any?>, callback : (Boolean, String) ->Unit) {
        repo.updateProfile(userID, userData, callback)
    }

    fun getUserData(email: String, callback: (UserModel?) -> Unit) {
        // Replace with your actual implementation to fetch user data
        // For Firebase example:
        /*
        firestore.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val userData = documents.first().toObject(UserModel::class.java)
                    callback(userData)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                callback(null)
            }
        */

        // Temporary simulation - replace with actual implementation
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            // Return sample data for testing
            val userData = UserModel(
                fullName = "Gaming Pro", // This should come from your database
                email = email,
                phoneNumber = "+1234567890", // This should come from your database
                address = "123 Gaming Street" // This should come from your database
            )
            callback(userData)
        }, 500)
    }

}