clear all
%close all

addpath('C:\Users\hnp1\Workspace\Project');
addpath('H:\0 Experimental results\20160727 Pull-off force calculation');

Data1=importdata('Dresult_2nm_4000nms_r2_0.28x250nm_L500nm_STV5%_deflection5_TipHeight.txt');
Data2=importdata('Dresult_2nm_4000nms_r2_0.28x250nm_L500nm_STV5%_deflection5.txt');
dx = 2;
dy = dx;
[n1, n2] = size(Data1);

x=1:dx:dx*n1;%linspace(0,10,1000); % 0 to 10 s, 1000 samples
y=1:dy:dy*n2;%logspace(1,3,1000); % 10^1 to 10^3, 1000 samples

myfilter = fspecial('gaussian',[3 3], 0.5);
SmoothenData = imfilter(Data2, myfilter, 'replicate');


% figure;
% imagesc(x,y,SmoothenData);
% xlabel('\bf X-axis (nm)','FontSize',14);
% ylabel('\bf Y-axis (nm)','FontSize',14);
% title('\bf Gaussian filter','FontSize',14);
% Ytick = linspace(min(y)-1,max(y)-1,5);
% set(gca, ...
%     'Ydir','normal', ...
%     'YTick', (Ytick), ...
%     'YTickLabel', ...
%     arrayfun(@num2str, Ytick(:), 'UniformOutput', false));



HistData = reshape(SmoothenData,1,numel(Data2));

figure;
hist(((HistData)),100)

% figure;
% surf(SmoothenData,'DisplayName','Force Map: Calculation','linestyle','none');figure(gcf)
% 
% I=mat2gray(SmoothenData);
% imwrite(I,'C:\Users\haesung\Documents\Work\Projects\hsp/result_10nm.tif')


figure;
colormap('gray');
imagesc(x,y,(Data1-min(min(Data1))).*1E+9);
colorbar;
xlabel('\bf X-axis (nm)','FontSize',14);
ylabel('\bf Y-axis (nm)','FontSize',14);
title('\bf Tip height map (nm)','FontSize',14);
Ytick = linspace(min(y)-1,max(y)-1,5);
set(gca, ...
    'Ydir','normal', ...
    'YTick', (Ytick), ...
    'YTickLabel', ...
    arrayfun(@num2str, Ytick(:), 'UniformOutput', false));

figure;
colormap('jet');
imagesc(x,y,(Data2).*1E+9);
colorbar;
xlabel('\bf X-axis (nm)','FontSize',14);
ylabel('\bf Y-axis (nm)','FontSize',14);
title('\bf Force map (nN) ','FontSize',14);
Ytick = linspace(min(y)-1,max(y)-1,5);
set(gca, ...
    'Ydir','normal', ...
    'YTick', (Ytick), ...
    'YTickLabel', ...
    arrayfun(@num2str, Ytick(:), 'UniformOutput', false));




