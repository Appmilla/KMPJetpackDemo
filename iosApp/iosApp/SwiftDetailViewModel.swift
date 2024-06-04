import Foundation
import Shared
import Combine

class SwiftDetailViewModel: ObservableObject {
    
    var viewModelStoreOwner = SharedViewModelStoreOwner<DetailViewModel>()
    var viewModel: DetailViewModel
    
    @Published
    private(set) var timerInterval: Int? = nil
    
    init() {
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
