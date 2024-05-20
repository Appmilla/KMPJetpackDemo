import Foundation
import Shared

class SwiftMainViewModel: ObservableObject {
    
    var viewModel: MainViewModel = KotlinDependencies.shared.getMainViewModel()
    
    @Published
    private(set) var timerInterval: Int? = nil

    @MainActor
    func activate() async {
        for await interval in viewModel.timer {
        
            self.timerInterval = Int(truncating: interval)
        }
    }
}
