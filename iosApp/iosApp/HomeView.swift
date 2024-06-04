import SwiftUI

struct HomeView: View {
    
    @ObservedObject
    var viewModel: SwiftHomeViewModel = SwiftHomeViewModel()
    
    @State private var navigationID = UUID().uuidString
    
    var body: some View {
        NavigationView {
            VStack {
                Text(viewModel.message)
                    .font(.largeTitle)
                    .padding()
                    
                
                NavigationLink(destination: DetailView(navigationID: $navigationID)) {
                    Text("Show DetailView")
                        .font(.largeTitle)
                        .padding()
                }
            }
            .onAppear {
                Task {
                    await viewModel.activate()
                    }
                }
            .navigationBarTitle("Home", displayMode: .inline)
        }
        
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}

