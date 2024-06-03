import Foundation
import Shared
import Combine

class SharedViewModelStoreOwner<VM: ViewModel>: ObservableObject, ViewModelStoreOwner {
    var viewModelStore: ViewModelStore = ViewModelStore()
    
    private let key: String = String(describing: VM.self)
    
    init() {
        let viewModel: VM = KotlinDependencies.shared.getKoin().get(objCClass: VM.self) as! VM
            viewModelStore.put(key: key, viewModel: viewModel)
        }
        
    
    var instance: VM {
        get {
            return viewModelStore.get(key: key) as! VM
        }
    }
    
    deinit {
        viewModelStore.clear()
    }
    
    func clearViewModel() {
        viewModelStore.clear()
    }
}


