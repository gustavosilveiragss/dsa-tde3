import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import numpy as np

df = pd.read_csv('results.csv')
df['execution_time_ms'] = df['execution_time_ns'] / 1_000_000
avg_df = df.groupby(['algorithm', 'array_size'])['execution_time_ms'].mean().reset_index()

def format_size(size):
    if size >= 1_000_000:
        return f'{size // 1_000_000}M'
    if size >= 1000:
        return f'{size // 1000}K'
    return str(size)

avg_df['array_size_formatted'] = avg_df['array_size'].apply(format_size)
df['array_size_formatted'] = df['array_size'].apply(format_size)

def add_labels(ax):
    for container in ax.containers:
        ax.bar_label(container, fmt='%.4f', label_type='edge', padding=3)

def plot_algorithm(algo_name, data, raw_data=None):
    plt.figure(figsize=(10, 6))
    
    if algo_name == "BogoSort" and raw_data is not None:
        ax = sns.barplot(data=raw_data[raw_data['algorithm'] == algo_name],
                         y='array_size_formatted',
                         x='execution_time_ms',
                         hue='round',  
                         orient='h')
        plt.legend(title='Round', bbox_to_anchor=(1.05, 1), loc='upper left')
    else:
        ax = sns.barplot(data=data[data['algorithm'] == algo_name],
                         y='array_size_formatted',
                         x='execution_time_ms',
                         orient='h')
        
    add_labels(ax)
    plt.title(f'Tempo de Execução - {algo_name}')
    plt.ylabel('Tamanho do Vetor')
    plt.xlabel('Tempo (ms)')
    plt.xscale('log')
    plt.tight_layout()
    plt.savefig(f'{algo_name.lower()}.png')
    plt.close()

def plot_comparison(algos, title, filename):
    plt.figure(figsize=(10, 6))
    comparison_data = avg_df[avg_df['algorithm'].isin(algos)]
    
    ax = sns.barplot(data=comparison_data,
                     y='array_size_formatted',
                     x='execution_time_ms',
                     hue='algorithm',
                     orient='h')
    add_labels(ax)
    plt.title(title)
    plt.ylabel('Tamanho do Vetor')
    plt.xlabel('Tempo (ms)')
    plt.xscale('log')
    plt.legend(title='Algoritmo', bbox_to_anchor=(1.05, 1), loc='upper left')
    plt.tight_layout()
    plt.savefig(filename, bbox_inches='tight')
    plt.close()

plot_comparison(['CocktailSort', 'BubbleSort'], 
               'Comparação: Cocktail Sort vs Bubble Sort',
               'cocktail_bubble.png')

plot_comparison(['ShellSort', 'InsertionSort'],
               'Comparação: Shell Sort vs Insertion Sort',
               'shell_insertion.png')

for algo in avg_df['algorithm'].unique():
    plot_algorithm(algo, avg_df, df)

fig, ax = plt.subplots(figsize=(12, 8))

sizes = avg_df['array_size_formatted'].unique()
algorithms = avg_df['algorithm'].unique()
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

for i, size in enumerate(sizes):
    size_data = avg_df[avg_df['array_size_formatted'] == size].sort_values('execution_time_ms')
    
    for j, (_, row) in enumerate(size_data.iterrows()):
        x = row['execution_time_ms']
        y = i - bar_width/2 + (j+0.5) * bar_spacing
        
        bar = ax.barh(y, x, height=bar_spacing, 
                     label=row['algorithm'] if i == 0 else "",  
                     color=colors[row['algorithm']],
                     alpha=0.8)
        
        ax.text(x * 1.02, y, f'{x:.4f}', 
                va='center', fontsize=8)

ax.set_yticks(y_positions)
ax.set_yticklabels(sizes)
ax.set_xscale('log')
plt.title('Comparação de Performance dos Algoritmos por Tamanho do Vetor')
plt.xlabel('Tempo Médio (ms)')
plt.ylabel('Tamanho do Vetor')
plt.legend(title='Algoritmo', bbox_to_anchor=(1.05, 1), loc='upper left')
plt.tight_layout()
plt.savefig('all.png', bbox_inches='tight')
plt.close()