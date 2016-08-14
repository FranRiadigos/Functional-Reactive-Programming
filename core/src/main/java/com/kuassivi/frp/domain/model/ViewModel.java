package com.kuassivi.frp.domain.model;

public class ViewModel<T> {

    public static class Builder<T> {

        private T viewModel;
        private boolean favourite;

        public Builder(T viewModel, boolean favourite) {
            this.viewModel = viewModel;
            this.favourite = favourite;
        }

        public T build() {
            if (this.viewModel instanceof VideoViewModel) {
                //noinspection unchecked
                return (T) new VideoViewModel((VideoViewModel)this.viewModel, this.favourite);
            }
            throw new ClassCastException("Builder for class " +
                    this.viewModel.getClass().getSimpleName() + " not implemented.");
        }
    }
}
