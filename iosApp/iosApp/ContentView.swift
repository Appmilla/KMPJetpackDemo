import SwiftUI
import Shared

struct ContentView: View {
    
    @ObservedObject
    var viewModel: SwiftMainViewModel

    init() {
        let viewModelStoreOwner = SharedViewModelStoreOwner<MainViewModel>()
        self.viewModel = SwiftMainViewModel(viewModelStoreOwner: viewModelStoreOwner)
    }
    
    @State private var showContent = false
    var body: some View {
        VStack {
            if let timerInterval = viewModel.timerInterval {
                            Text("\(timerInterval)")
                                .font(.largeTitle)
                                .padding()
                        } else {
                            Text("Loading...")
                                .font(.largeTitle)
                                .padding()
                        }
            
         
            }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
        .padding()
        .onAppear {
                    Task {
                        await viewModel.activate()
                    }
                }
        .onDisappear {
            viewModel.deactivate()
            }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
