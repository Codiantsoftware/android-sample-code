package com.example.myapplication.presentation.base

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.myapplication.util.DialogUtil
import org.json.JSONObject

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<*>> : Fragment(),
    CommonNavigator {

    private var baseActivity: BaseActivity<*, *>? = null
    private var mRootView: View? = null
    private var viewDataBinding: T? = null
    private var mViewModel: V? = null

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract val bindingVariable: Int

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract val viewModel: V

    /**
     * This method is called first even before onCreate(),
     * letting us know that your fragment has been attached to an activity.
     *
     * @param context instance of class.
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            val activity = context as BaseActivity<*, *>?
            this.baseActivity = activity
            activity!!.onFragmentAttached()
        }
    }

    override fun showToast(msg: String?) {
        this.baseActivity!!.showToast(msg)
    }

    /**
     * This method is used for creating the fragment and
     * initialize essential components of the fragment.
     *
     * @Bundle parameter in which the fragment can save data.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = viewModel
        DialogUtil.checkMsg = ""
        @Suppress("DEPRECATION")
        setHasOptionsMenu(false)

    }


    /**
     * This method is used for draw its user interface for
     * the first time and return view.
     *
     * @Bundle parameter in which the fragment can save data.
     * @ViewGroup parent view group into which the view of fragment to be inserted.
     * @LayoutInflater can create view instance based on layout XML files.
     *
     * @return view return fragment view.
     *
     */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        viewDataBinding!!.lifecycleOwner = this
        mRootView = viewDataBinding!!.root
        return mRootView
    }


    /**
     * This method is called after onDestroy(), to notify that the
     * fragment has been disassociated from its hosting activity.
     */
    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }


    /**
     * This method is called after onCreateView() this is particularly useful
     * when inheriting the onCreateView() implementation but
     * we need to configure the resulting views.
     *
     * @Bundle parameter in which the fragment can save data.
     * @View  basic building block for user interface components.
     *
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.executePendingBindings()
        mRootView.apply {
            this!!.isClickable = true
            this.isFocusable = true
        }
        init()


    }


    override fun checkInternet(tryAgain: () -> Unit): Boolean {
        return try {
            baseActivity!!.checkInternet(tryAgain)
        }catch (e:Exception){
            false
        }
    }


    /**
     * This method is used for check dynamic permission.
     *
     * @Array list of permissions.
     * @Context instance of current class or activity.
     *
     * @return check and return true or false.
     */
    private fun hasAllPermissionsGranted(grantResults: IntArray): Boolean {
        for (grantResult in grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false
            }
        }
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {

        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 && hasAllPermissionsGranted(grantResults)) {
            invokedWhenPermissionGranted()
        } else if (requestCode == 111) {
            invokedDeniedWithResult(grantResults)
        }
    }

    protected open fun invokedDeniedWithResult(grantResults: IntArray) {}

    protected open fun invokedWhenPermissionGranted() {}


    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }


    /**
     * This method is used for show progress.
     */
    override fun showProgress() {
        try {
            baseActivity!!.showProgress()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    /**
     * This method is used for hide progress.
     */
    override fun hideProgress() {
        try {
            baseActivity!!.hideProgress()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    /**
     * This method is used for set root visibility.
     */
    override fun setRootViewVisibility() {

    }


    /**
     * This method is used to get string from string file.
     *
     * @param id is string name from string xml file.
     *
     */
    override fun getStringResource(id: Int): String {
        return resources.getString(id)
    }

    /**
     * This method is used to get resource file by using id.
     *
     * @param id is resource file id.
     */
    override fun getIntegerResource(id: Int): Int {
        return resources.getInteger(id)
    }

    /**
     * This method is used to go to back page.
     */
    override fun onBackClick() {
        baseActivity!!.onBackClick()
    }



    override fun showAlertDialog(
        alertTitle: String?,
        alertMsg: String,
        leftButtonTitle: String?,
        rightButtonTitle: String?,
        bottomTextTitle: String?,
        showCross: Boolean,
        imageResId: Int?,
        onClickLeftButton: () -> Unit,
        onClickRightButton: () -> Unit,
        onClickBottomText: () -> Unit,
        onCrossClick: () -> Unit
    ) {
        this.baseActivity!!.showAlertDialog(
            alertTitle,
            alertMsg,
            leftButtonTitle,
            rightButtonTitle,
            bottomTextTitle,
            showCross,
            imageResId,
            onClickLeftButton,
            onClickRightButton,
            onClickBottomText,
            onCrossClick
        )
    }

    override fun handleApiResponseHandler(jsonObject: JSONObject, statusCode: Int) {
        try {
            this.baseActivity!!.handleApiResponseHandler(jsonObject, statusCode)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun handleApiResponseException(e: Exception) {
        try {
            this.baseActivity!!.handleApiResponseException(e)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onDestroyView() {
        baseActivity!!.hideProgress()
        super.onDestroyView()
    }
}
