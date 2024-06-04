import SwiftUI
import Shared

struct DetailView: View {
    @Binding var navigationID: String

    @StateObject
    var viewModel = SwiftDetailViewModel()
    
    
    init(navigationID: Binding<String>) {
        self._navigationID = navigationID
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
            // When the view disappears, update the navigation ID to ensure recreation next time
            self.navigationID = UUID().uuidString
            }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        DetailView(navigationID: .constant(UUID().uuidString))
    }
}
