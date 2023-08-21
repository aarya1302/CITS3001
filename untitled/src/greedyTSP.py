def greedy_tsp(N, distances):
    tour = []
    visited = [False] * N

    city = 0

    nn = None

    tour.append(city)
    visited[city] = True
    while len(tour) < N:
        n = 0
        min_dist = 0
        for n_dist in distances[city]:
            if n_dist > 0 and not visited[n]: 
                if min_dist == 0 or min_dist > n_dist: 
                    min_dist = n_dist
                    nn = n
            n += 1
        tour.append(nn)
        visited[nn] = True
        city = nn
    tour.append(0)
    return tour

def calculate_dist(tour, distances): 
    total = 0
    for i in range(len(tour) - 1): 
        total += distances[tour[i]][tour[i+1]]
    return total

def optimize_tour_with_2opt(N, distances): 
    tour = greedy_tsp(N, distances)
    curr_dist = calculate_dist(tour, distances)

    for i in range(1, len(tour) - 2):
        for j in range(i + 1, len(tour) - 1): 
            new_tour = tour[:]
            new_tour[i:j+1] = reversed(new_tour[i:j+1])
            if calculate_dist(new_tour, distances) < curr_dist:
                tour = new_tour
                curr_dist = calculate_dist(tour, distances)
    
    return tour, curr_dist