import Foundation
import Shared
import Combine

class SwiftHomeViewModel: ObservableObject {
    
    var viewModelStoreOwner = SharedViewModelStoreOwner<HomeViewModel>()
    
    var viewModel: HomeViewModel
    
    @Published
    private(set) var message: String = ""

    init() {
        let viewModel = viewModelStoreOwner.instance
        self.viewModel = viewModel
    }
    
    @MainActor
    func activate() async {
        for await message in viewModel.message {
            self.message = message
        }
    }

    func deactivate() {
        viewModelStoreOwner.clearViewModel()
    }
}

