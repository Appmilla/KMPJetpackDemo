import Foundation
import Shared
import Combine

class SwiftMainViewModel: ObservableObject {
    
    var viewModel: MainViewModel = KotlinDependencies.shared.getMainViewModel()
    
    @Published
    private(set) var timerInterval: Int? = nil

    private var viewModelStoreOwner: SharedViewModelStoreOwner<MainViewModel>
    
    init(viewModelStoreOwner: SharedViewModelStoreOwner<MainViewModel>) {
        self.viewModelStoreOwner = viewModelStoreOwner
        let viewModel = viewModelStoreOwner.instance
        self.viewModel = viewModel
    }

    @MainActor
    func activate() async {
        for await interval in viewModel.timer {
        
            self.timerInterval = Int(truncating: interval)
        }
    }

    func deactivate() {
        viewModelStoreOwner.clearViewModel()
    }
}
