package br.com.bruno.meumetro.interfaces;

/**
 * Created by Bruno on 28/08/2016.
 */
public interface IServiceResponse<T> {
    void onSuccess(T data);
    void onError();
}
