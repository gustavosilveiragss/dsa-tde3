import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import numpy as np

def format_size(size):
    if size >= 1_000_000:
        return f'{size // 1_000_000}M'
    if size >= 1000:
        return f'{size // 1000}K'
    return str(size)

def add_labels(ax):
    for container in ax.containers:
        ax.bar_label(container, fmt='%.2f', label_type='edge', padding=3)

def plot_metric(algo_name, data, metric, raw_data=None):
    plt.figure(figsize=(10, 6))
    
    if algo_name == "BogoSort" and raw_data is not None:
        ax = sns.barplot(data=raw_data[raw_data['algorithm'] == algo_name],
                         y='array_size_formatted',
                         x=metric,
                         hue='round',  
                         orient='h')
        plt.legend(title='Round', bbox_to_anchor=(1.05, 1), loc='upper left')
    else:
        ax = sns.barplot(data=data[data['algorithm'] == algo_name],
                         y='array_size_formatted',
                         x=metric,
                         orient='h')
    
    metric_name = 'Tempo (ms)' if metric == 'execution_time_ms' else \
                 'Número de Trocas' if metric == 'swaps' else 'Número de Iterações'
    
    add_labels(ax)
    plt.title(f'{metric_name} - {algo_name}')
    plt.ylabel('Tamanho do Vetor')
    plt.xlabel(metric_name)
    plt.xscale('log')
    plt.tight_layout()
    plt.savefig(f'{algo_name.lower()}_{metric}.png')
    plt.close()

def plot_comparison(algos, metric, title, filename):
    plt.figure(figsize=(10, 6))
    comparison_data = avg_df[avg_df['algorithm'].isin(algos)]
    
    ax = sns.barplot(data=comparison_data,
                     y='array_size_formatted',
                     x=metric,
                     hue='algorithm',
                     orient='h')
    
    metric_name = 'Tempo (ms)' if metric == 'execution_time_ms' else \
                 'Número de Trocas' if metric == 'swaps' else 'Número de Iterações'
    
    add_labels(ax)
    plt.title(title)
    plt.ylabel('Tamanho do Vetor')
    plt.xlabel(metric_name)
    plt.xscale('log')
    plt.legend(title='Algoritmo', bbox_to_anchor=(1.05, 1), loc='upper left')
    plt.tight_layout()
    plt.savefig(filename, bbox_inches='tight')
    plt.close()

def plot_all_algorithms_comparison(df, metric):
    fig, ax = plt.subplots(figsize=(12, 8))

    sizes = df['array_size_formatted'].unique()
    algorithms = df['algorithm'].unique()
    n_sizes = len(sizes)
    n_algorithms = len(algorithms)

    colors = {
        'InsertionSort': '#1f77b4',
        'ShellSort': '#ff7f0e',
        'CocktailSort': '#2ca02c',
        'MergeSort': '#d62728',
        'BubbleSort': '#9467bd',
        'BogoSort': '#8c564b'
    }

    bar_width = 0.8
    bar_spacing = bar_width / n_algorithms
    y_positions = np.arange(n_sizes)

    metric_name = 'Tempo Médio (ms)' if metric == 'execution_time_ms' else \
                 'Número Médio de Trocas' if metric == 'swaps' else 'Número Médio de Iterações'

    for i, size in enumerate(sizes):
        size_data = df[df['array_size_formatted'] == size].sort_values(metric)
        
        for j, (_, row) in enumerate(size_data.iterrows()):
            x = row[metric]
            y = i - bar_width/2 + (j+0.5) * bar_spacing
            
            bar = ax.barh(y, x, height=bar_spacing,
                         label=row['algorithm'] if i == 0 else "",
                         color=colors[row['algorithm']],
                         alpha=0.8)
            
            ax.text(x * 1.02, y, f'{x:.2f}',
                    va='center', fontsize=8)

    ax.set_yticks(y_positions)
    ax.set_yticklabels(sizes)
    ax.set_xscale('log')
    plt.title(f'Comparação de {metric_name} por Tamanho do Vetor')
    plt.xlabel(metric_name)
    plt.ylabel('Tamanho do Vetor')
    plt.legend(title='Algoritmo', bbox_to_anchor=(1.05, 1), loc='upper left')
    plt.tight_layout()
    plt.savefig(f'all_{metric}.png', bbox_inches='tight')
    plt.close()

def plot_relative_performance(df):
    analysis_df = df[df['algorithm'] != 'BogoSort'].copy()
    avg_data = analysis_df.groupby(['algorithm', 'array_size']).agg({
        'execution_time_ms': 'mean'
    }).reset_index()
    
    sizes = sorted(avg_data['array_size'].unique())
    relative_data = []
    
    for size in sizes:
        size_data = avg_data[avg_data['array_size'] == size]
        min_time = size_data['execution_time_ms'].min()
        
        for _, row in size_data.iterrows():
            relative_data.append({
                'algorithm': row['algorithm'],
                'array_size': size,
                'array_size_formatted': format_size(size),
                'relative_time': row['execution_time_ms'] / min_time
            })
    
    relative_df = pd.DataFrame(relative_data)
    
    plt.figure(figsize=(12, 6))
    ax = sns.barplot(data=relative_df,
                    x='array_size_formatted',
                    y='relative_time',
                    hue='algorithm')
    
    plt.title('Desempenho Relativo ao Algoritmo Mais Rápido')
    plt.xlabel('Tamanho do Vetor')
    plt.ylabel('Tempo Relativo (1 = mais rápido)')
    plt.yscale('log')
    plt.xticks(rotation=45)
    plt.legend(title='Algoritmo', bbox_to_anchor=(1.05, 1), loc='upper left')
    
    for i in ax.containers:
        ax.bar_label(i, fmt='%.1fx', label_type='edge', padding=2)
    
    plt.tight_layout()
    plt.savefig('relative_performance.png', bbox_inches='tight', dpi=300)
    plt.close()

def generate_summary_tables():
    summary_table = avg_df.round(2)
    
    with open('summary_metrics.md', 'w') as f:
        f.write('### Métricas Médias por Algoritmo e Tamanho\n\n')
        f.write('| Algoritmo | Tamanho | Tempo (ms) | Trocas | Iterações |\n')
        f.write('|-----------|----------|------------|---------|------------|\n')
        
        for _, row in summary_table.iterrows():
            f.write(f"| {row['algorithm']} | {row['array_size_formatted']} | {row['execution_time_ms']:.2f} | {row['swaps']:.0f} | {row['iterations']:.0f} |\n")

def generate_comparative_tables():
    analysis_df = df[df['algorithm'] != 'BogoSort'].copy()
    avg_data = analysis_df.groupby(['algorithm', 'array_size']).agg({
        'execution_time_ms': 'mean'
    }).reset_index()
    
    sizes = sorted(avg_data['array_size'].unique())
    
    for size in sizes:
        size_data = avg_data[avg_data['array_size'] == size]
        min_time = size_data['execution_time_ms'].min()
        
        with open(f'comparative_size_{size}.md', 'w') as f:
            f.write(f'### Comparação de Tempo de Execução - Tamanho {format_size(size)}\n\n')
            f.write('| Algoritmo | Tempo (ms) | Tempo Relativo | Posição |\n')
            f.write('|-----------|------------|----------------|----------|\n')
            
            sorted_data = size_data.sort_values('execution_time_ms')
            for idx, row in sorted_data.iterrows():
                relative = row['execution_time_ms'] / min_time
                f.write(f"| {row['algorithm']} | {row['execution_time_ms']:.2f} | {relative:.2f}x | {sorted_data.index.get_loc(idx) + 1}º |\n")

def generate_algorithm_growth_table():
    analysis_df = df[df['algorithm'] != 'BogoSort'].copy()
    base_size = analysis_df['array_size'].min()
    
    growth_data = []
    for algo in analysis_df['algorithm'].unique():
        algo_data = analysis_df[analysis_df['algorithm'] == algo].groupby('array_size')['execution_time_ms'].mean()
        base_time = algo_data[base_size]
        
        for size in algo_data.index:
            growth = algo_data[size] / base_time
            growth_data.append({
                'algorithm': algo,
                'size': size,
                'growth_factor': growth
            })
    
    growth_df = pd.DataFrame(growth_data)
    
    with open('growth_analysis.md', 'w') as f:
        f.write('### Análise de Crescimento dos Algoritmos\n\n')
        f.write('| Algoritmo | Tamanho | Fator de Crescimento |\n')
        f.write('|-----------|----------|--------------------|\n')
        
        for _, row in growth_df.sort_values(['algorithm', 'size']).iterrows():
            f.write(f"| {row['algorithm']} | {format_size(row['size'])} | {row['growth_factor']:.2f}x |\n")


df = pd.read_csv('results.csv')
df['execution_time_ms'] = df['execution_time_ns'] / 1_000_000
df['array_size_formatted'] = df['array_size'].apply(format_size)
avg_df = df.groupby(['algorithm', 'array_size']).agg({
    'execution_time_ms': 'mean',
    'swaps': 'mean',
    'iterations': 'mean'
}).reset_index()
avg_df['array_size_formatted'] = avg_df['array_size'].apply(format_size)

metrics = ['execution_time_ms', 'swaps', 'iterations']
for metric in metrics:
    for algo in avg_df['algorithm'].unique():
        plot_metric(algo, avg_df, metric, df)
    
    plot_comparison(
        ['CocktailSort', 'BubbleSort'],
        metric,
        f'Comparação: Cocktail Sort vs Bubble Sort - {metric}',
        f'cocktail_bubble_{metric}.png'
    )
    
    plot_comparison(
        ['ShellSort', 'InsertionSort'],
        metric,
        f'Comparação: Shell Sort vs Insertion Sort - {metric}',
        f'shell_insertion_{metric}.png'
    )
    
    plot_all_algorithms_comparison(avg_df, metric)

plot_relative_performance(df)

generate_summary_tables()
generate_comparative_tables()
generate_algorithm_growth_table()